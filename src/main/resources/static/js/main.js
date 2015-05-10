//TODO: Select all emotions if not emotions are selected


            //Stores a number of inscriptions in multiple hashes by city -> type -> language -> religion

            var selected = []
            var names = [];
            var positions = [];
            var sentenceFacets = [];
            var allSentenceFacets = ["sad", "happy", "stress", "fear", "war", "love", "crime", "angry", "alcohol"];
            var bookFacets = [];

            var slider_min = 1500;
            var slider_max = 2000;
            var slider_start = slider_min;
            var slider_end = slider_max;
            var circles = undefined;
            var maker = undefined;
            var svg_map = undefined;
            var g = undefined;
            var unchanged = false;
            var pending = false;
            //var circle_artist = undefined;
            var author = undefined;
            var facetType = "location";
            var bubbleSize = 400;//document.getElementById("author").offsetWidth*0.8;
            //Load the JSON files
            $.post("/location", {}, function(responseJSON) {
                var responseObject = JSON.parse(responseJSON);
                responseObject.locations.forEach(function(d,i) {
                    names[i] = d.name;
                    var layer_point = map.latLngToLayerPoint(d.coordinates);
                    positions[i] = [layer_point.x, layer_point.y];
                });
                makeCircles();
            });


            $(document).ready(function(){

                //handle shuffling the invisible divs
                document.getElementById(facetType + "_child").style.display = "inline";
                $("#"+facetType).addClass("btn-success");

                //Draw the Map using the MapBox Tiling - ZOOMING IS DISABLED
                tile = 'http://{s}.tile.stamen.com/watercolor/{z}/{x}/{y}.jpg'
                map = L.map('map').setView([31, 15], 2);
                L.tileLayer(tile, {
                    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
                    maxZoom: 2,
                    minZoom: 2
                }).addTo(map);

                // map.dragging.disable();
                // map.touchZoom.disable();
                // map.doubleClickZoom.disable();
                // map.scrollWheelZoom.disable();

                // Disable tap handler, if present.
                if (map.tap) map.tap.disable();

                //SVG Object that D3 circles lie on top of
                map._initPathRoot();





                svg_map = d3.select("#map").select("svg")
                g = svg_map.append("g");
                //initializing the slider
                $("#slider").slider({ values: [slider_min,slider_max]});
                $("#slider").slider({ min: slider_min});
                $("#slider").slider({ max: slider_max});
                $( "#label_div" ).append($('<label id="leftLabel">'+(slider_min + " or before" )+'</label>').css("float","left"));
                $( "#label_div" ).append($('<label id="rightLabel">'+(slider_max)+'</label>').css("float","right"));
                $("#slider").on( "slidechange", function(event,ui) {
                    unchanged = false;
                    Minimum = $("#slider").slider("values",0);
                    Maximum = $("#slider").slider("values",1);
                    slider_start = Math.min(Minimum,Maximum);
                    slider_end = Math.max(Minimum,Maximum);
                    document.getElementById("leftLabel").innerHTML = slider_start == slider_min ? 1500 + " or before" : slider_start;
                    document.getElementById("rightLabel").innerHTML = slider_end;
                });



                //fill author box with circles
                $.post("/author", {}, function(responseJSON) {
                    var responseObject = JSON.parse(responseJSON);

                    newJSON = {"children" : []};

                    for (var i = 0; i < responseObject.authors.length; i ++) {
                        var name = responseObject.authors[i];
                        var obj = {
                            name: name,
                            className: name,
                            size: 100
                        }
                        newJSON["children"].push(obj);
                    }
                    //on author change, set unchanged to false
                    var circle_artist = new circleArtist(newJSON, "author", bubbleSize);
                    author = circle_artist.get_selected();
                    document.getElementById("author").onclick = function() {
                        author = circle_artist.get_selected();
                        unchanged = false
                    };


                })


                //fill facet box with circles
                $.post("/facet", {}, function(responseJSON) {
                    var responseObject = JSON.parse(responseJSON);
                    newJSON = {"children" : []};
                    for (var i = 0; i < responseObject.facets.length; i ++) {
                        var name = responseObject.facets[i];
                        var obj = {
                            name: name,
                            className: name,
                            size: 100
                        }
                        newJSON["children"].push(obj);
                    }
                    var circle_artist_facets = new circleArtistMulti(newJSON, "type_child", bubbleSize);
                    document.getElementById("type_child").onclick = function() {
                        bookFacets = circle_artist_facets.get_selected_facets();
                        unchanged = false
                    };
                })
            });

            function submit() {
                // if (selected.length == 0) {
                //     alert("Please select at least one location")
                //     return
                // }
                document.getElementById("show_parse").style.display = "";
                if (author == undefined) {
                    console.log("AUTHOR ERROR");
                    return;
                }
                if (!pending) {
                    var pending = true;
                    document.getElementById("results_div").innerHTML = " <h1> Generating Phrase... </h1>";
                    //Get all selected facets from multiple select

        			var postParameters = {
                        unchanged: unchanged ? "yes" : "no",
        				author: author,//document.getElementById("author_select").value,
                        //facets: bookFacets,
                        type: facetType,
        				date_start: (facetType != "time" || slider_start == slider_min) ? -2000 : slider_start,
        				date_end: facetType == "time" ? slider_end : slider_max
        			};
                    //locations
        			for (var i = 0; i < selected.length; i++) {
        				postParameters["l" + i] = selected[i];
        			}
                    //sentence facets
                    if (sentenceFacets.length > 0) {
                        F = sentenceFacets;
                    } else {
                        F = allSentenceFacets;
                    }
                    for (var i = 0; i < F.length; i++) {
                        postParameters["f" + i] = F[i];
                    }
                    //books facets
                    for (var i = 0; i < bookFacets.length; i++) {
                        postParameters["bf" + i] = bookFacets[i];
                    }
                    unchanged = true;
            		$.post("/results", postParameters, function(responseJSON){
            			var responseObject = JSON.parse(responseJSON);
                        results_div.innerHTML = "<h1>" + responseObject.sentence + "</h1>";
                        if (responseObject.tree.length > 0) {
                            document.getElementById("show_parse").style.display = "inline";
                            document.getElementById("parse_div").innerHTML = "";
                            var json = JSON.parse(responseObject.tree);
                            var tree_artist = new treeArtist(json, "parse_div", [1000, 1500]);
                        } else {
<<<<<<< HEAD
                            document.getElementById("parse_div").innerHTML = "Sorry, this sentence has a parsing issue. It may be too long, or it may have an unusual structure. Please try again!";
=======
                            document.getElementById("show_parse").style.display = "";
                            document.getElementById("parse_div").innerHTML = "Sorry, this sentence is too long to parse. Please try again!";
>>>>>>> 436b8963ce37085d8120433df82b6a07658b2ae1
                        }
                        pending = false;
            		})
                }
            }

            //responds when a facet image is clicked
            function imageClick(id) {
                var index = sentenceFacets.indexOf(id);
                if (index != -1) {
                    sentenceFacets.splice(index, 1);
                    $("#"+id).removeClass("btn-success");
                    console.log(document.getElementById(id));
                } else {
                    sentenceFacets.push(id);
                    $("#"+id).addClass("btn-success");
                    console.log(document.getElementById(id));
                }
                unchanged = false;
            }

            //responds when the radioButtonDiv is changed
            function facetClick(id) {
                unchanged = false;
                $("#"+facetType).removeClass("btn-success");
                document.getElementById(facetType + "_child").style.display = "";
                facetType = id;
                $("#"+facetType).addClass("btn-success");
                document.getElementById(facetType + "_child").style.display = "inline";
            }

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

            //returns the color of the input circle id, based on the different factors involved
            function get_color(id) {
                return contains(selected, id) ? "red" : "blue";
            }

             makeCircles = function() {
                circles = svg_map.selectAll("circle")
                    .data(names)
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
                    .on('click',function(d) {console.log(555); circle_click_map(this)})
                    .on('mouseover',function(d) {circle_mouseover(this)})
                    .on("mouseleave", function(d) {circle_mouseleave(this)})
                    .style("opacity", .6)
                    .style("stroke", 0)
                    .style("stroke-width", 0)
                circles.transition()
                    //The transition takes one second
                    .duration(500)
                    .attr("r", function(n) {
                        return 10;
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
                console.log(e.id)
                d3.select(e)
                    .style("fill", "red");
                var n = e.id;
                label.innerHTML = e.id;
            }

            //responds when circle is clicked - add or remove a circle from selected
            circle_click_map = function(e) {
                unchanged = false;
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

            showParsed = function() {
                document.getElementById("parse_wrapper").style.display = "inline";
                $("html, body").animate({ scrollTop: 0 }, "slow");
            }

            hideParsed = function() {
                document.getElementById("parse_wrapper").style.display = "";
                $("html, body").animate({scrollTop: $("#big_4").offset().top}, "slow");
            }




                    //     document.getElementById("author_select").innerHTML += "<option value=\" "+ name +"\"> " + name +"</option>"
                    // }
                    // //on author change, set unchanged to false
                    // //document.getElementById("author_select").onchange = function() {unchanged = false};


