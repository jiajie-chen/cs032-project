
            var location_dict = {};
            //Stores a number of inscriptions in multiple hashes by city -> type -> language -> religion
            var multiHash = {};
            //stores sizes of location circles
            var totalSizes = {};
            var sizes = {};
            //array of all currenty selected locations
            var selected = []
            //This array stores the unsimplified names of the location (unsimplified keys of location_dict) -> only locations that have books published in the current time period are included
            var names = [];
            var positions = [];

            var slider_min = -2000;
            var slider_max = 2000;
            var slider_start = slider_min;
            var slider_end = slider_max;
            var Json = "data/coordinateJSON.json"
            var circles = undefined;
            var maker = undefined;
            var svg = undefined;
            var g = undefined;
            
            //Load the JSON files
            d3.json(Json, function(locations) {
                locations.rows.forEach(function(d,i) {
                    location_dict[d.name] = d;
                    multiHash[d.name] = {};
                    names[i] = d.name;
                    layer_point = map.latLngToLayerPoint(d.coordinates);
                    positions[i] = [layer_point.x, layer_point.y];
                });
                maker = new circleManager();
                updateSizes();
            });
            
            $(document).ready(function(){
                //Draw the Map using the MapBox Tiling
                tiles = ['http://{s}.tile.stamen.com/watercolor/{z}/{x}/{y}.jpg',
                         'http://{s}.tiles.mapbox.com/v3/danshiebler.lfa46a3b/{z}/{x}/{y}.png',
                         'http://{s}.tiles.mapbox.com/v3/danshiebler.jc7h07lm/{z}/{x}/{y}.png',
                         'http://{s}.tiles.mapbox.com/v3/danshiebler.ip7j62mf/{z}/{x}/{y}.png']

                map = L.map('map').setView([31, 15], 2);
                L.tileLayer(tiles[0], {
                    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
                    maxZoom: 18
                }).addTo(map);
                //SVG Object that D3 circles lie on top of
                map._initPathRoot();
                svg = d3.select("#map").select("svg")
                g = svg.append("g");
                //initializing the slider
                $("#slider").slider({ values: [slider_min,slider_max]});
                $("#slider").slider({ min: slider_min});
                $("#slider").slider({ max: slider_max});
                $( "#label_div" ).append($('<label id="leftLabel">'+(slider_min)+'</label>').css("float","left"));
                $( "#label_div" ).append($('<label id="rightLabel">'+(slider_max)+'</label>').css("float","right"));
                $("#slider").on( "slidechange", function(event,ui) {
                    Minimum = $("#slider").slider("values",0);
                    Maximum = $("#slider").slider("values",1);
                    console.log(Minimum)
                    console.log(Maximum)
                    slider_start = Math.min(Minimum,Maximum);
                    slider_end = Math.max(Minimum,Maximum);
                    document.getElementById("leftLabel").innerHTML = slider_start;
                    document.getElementById("rightLabel").innerHTML = slider_end;
                });
                //initializing the author slider
                //TODO: Change this part to a get request of all author's names
                for (var i = 0; i < 5; i ++) {
                    document.getElementById("author_select").innerHTML += "<option value=\" author_"+ i +"\"> author_" +i+"</option>"
                }

            });

            function submit() {
                //TODO: Change this by adding AJAX, sending this data and getting a sentence in return
                // if (document.getElementById("author_select").value == undefined) {
                //     alert("Please select an author")
                //     return
                // } 
                if (selected.length == 0) {
                    alert("Please select at least one location")
                    return
                }
    			var postParameters = { 
    					author: document.getElementById("author_select").value,
    					date_start: slider_start,
    					date_end: slider_end
    					//facets will go here next
    			};
    			for (var i = 0; i < selected.length; i++) {
    				postParameters["l" + i] = selected[i];
    			}
    			console.log(postParameters)
        		$.post("/results", postParameters, function(responseJSON){
        			var responseObject = JSON.parse(responseJSON);
                    results_div.innerHTML = responseObject.sentence;
        		})
            }


            function reset_map() {
                if (circles!=undefined) {
                    maker.update();
                } else {
                    console.log("Circles not initialized")
                }
            };


            //Check membership in an array
            function contains(a, obj) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i] === obj) {
                        return true;
                    }
                }
                return false;
            }

            function sum(array){
                var count = 0;
                for (var i = array.length; i--;) {
                    count += array[i];
                }
                return count;
            }

            //converts a number of inscriptions to a circle size
            function num2size(num) {
                return num <= 0 ? 2 : Math.ceil(Math.log((num+1)*2),2)*2 + 1
            }

            //accepts the name of a location, returns number of inscriptions, privy to current settings
            function getSize(location) {
                return 10;
                // var simpName = simplify_string(location);
                // var size = 0;
                // for (var t in multiHash[simpName]) {
                //     if (types[t] == true) {
                //         for (var l in multiHash[simpName][t]) {
                //             if (languages[l] == true) {
                //                 for (var r in multiHash[simpName][t][l]) {
                //                     if (religions[r] == true) {
                //                         size += multiHash[simpName][t][l][r];
                //                     }
                //                 }
                //             }
                //         }
                //     }
                // }
                // return num2size(size);
            }

            //writes the contents of the selected array to the selected div
            function writeSelected() {
                string = "The selected places are: </br>";
                for (var i = 0; i < selected.length; i++) {
                    string += selected[i] + "</br>"
                }
                selected_div.innerHTML = string;

            }

            //returns the color of the input circle id, based on the different factors involved
            function get_color(id) {
                return contains(selected, id) ? "red" : "blue";
            }

            function updateSizes() {
                for (var i = 0; i < names.length; i++) {
                    name = names[i];
                    sizes[name] = getSize(name);
                }
            }

            /*
                circle manager object prototype - handles voronoi and circles stuff
            */

            function circleManager(){

                //only call this once
                this.build = function() {
                    this.makeCircles();
                    map.on("viewreset", this.update);      
                }

                //tied to the build function
                this.update = function() {
                    updateSizes();
                    circles.attr("transform", function(n) { 
                            layer_point = map.latLngToLayerPoint(location_dict[n].coordinates);
                            return "translate(" +
                            layer_point.x +"," +
                            layer_point.y +")";
                    });

                    text.attr("transform", function(n) { 
                            layer_point = map.latLngToLayerPoint(location_dict[n].coordinates);
                            return "translate(" +
                            layer_point.x +"," +
                            layer_point.y +")";
                    });

                    circles.transition()
                        //The transition takes one second
                        .duration(1000)
                        //Here we set the radius of the circle element
                        .attr("r", function(n) { 
                            //if there are no books at that place in the current parameter window - radius is 0
                            return sizes[n];
                        });
                    circles.style("fill", function(d) {return get_color(d)})

                    if (map.getZoom() > 9) {
                        text.style("opacity", 1)
                    } else {
                        text.style("opacity", 0)
                    }
                }

                this.makeCircles = function() {
                    updateSizes();

                    text = g.selectAll("text")
                            .data(names)
                            .enter()
                            .append("text")
                            .text(function(d) {return d})
                            .style("opacity", 0)
                            .attr("transform", function(d,i) { 
                                return "translate(" + positions[i] + ")"; 
                            });



                    circles = g.selectAll("circle")
                        .data(names
                            //the circles' locations are determined here
                            .sort(function(a, b) {
                                return b.size - a.size; 
                            })
                        )
                        .enter()
                        .append("circle")
                        .attr("transform", function(d,i) { 
                            return "translate(" + positions[i] + ")"; 
                        })
                        //Here we assign a class to the circle, which allows us to select it and change its color on mouseover later
                        .attr('id',function(n,i){
                            //Assign an id. 
                            var id = n;
                            return id;
                        })
                        .attr("r",0)
                        .on('click',function(d) {circle_click(this)})
                        .on('mouseover',function(d) {circle_mouseover(this)})//this.voronoi_mouseover)
                        .on("mouseleave", function(d) {circle_mouseleave(this)})
                        .style("opacity", .6)
                        .style("stroke", 0)
                        .style("stroke-width", 0)
                    circles.transition()
                        //The transition takes one second
                        .duration(500)
                        //Here we set the radius of the circle element
                        .attr("r", function(n) { 
                            //if there are no books at that place in the current parameter window - radius is 0
                            return sizes[n];
                        });
                    circles.style("fill", function(d) {return get_color(d)})
                }

                //Responds when voronoi is moused over
                circle_mouseleave = function (e) {
                    color = get_color(e.id);
                    d3.select(e)
                        .style("fill", color)
                    label.innerHTML = "Click a Circle to Select a Location";
                }

                //Responds when voronoi is moused over
                circle_mouseover = function (e) {
                    d3.select(e)
                        .style("fill", "red");
                    var n = e.id;
                    label.innerHTML = e.id;
                }

                //responds when circle is clicked - add or remove a circle from selected
                circle_click = function(e) {
                    simp = e.id
                    var index = selected.indexOf(simp);
                    if (index != -1) {
                        selected.splice(index, 1);
                    } else {
                        selected.push(simp);
                    }
                    //writeSelected();
                    d3.select(e)
                        .style("fill", get_color(e.id))
                }

                //Mainline
                this.build();

            }
//             .book_box_buttons { 
//     background:none;
//     border:none; 

// }

// #facet_div {
//     position: fixed;
//     left: 10%;
// }

// #button_div {
//     position: fixed;
//     top:50%;
//     right: 5%;
// }

// #selected_div {
//     height: 200px;
//     overflow: scroll;
//     position: fixed;
//     top:55%;
//     right: 5%;
// }
