//Class function for the circle artist


function treeArtist(json, divId, size) {

    this.tree = d3.layout.tree()
        .size([size, size]);
        // .children(function(d) 
        // {
        //     console.log(d._children);
        //     (!d._children || d.children.length === 0) ? null : d._children;
        // });


    this.nodes = this.tree.nodes(json);
    this.links = this.tree.links(nodes);
    console.log(json)
    console.log(this.nodes)
    console.log(this.links)

    this.svg = d3.select('#' + divId).append("svg:svg")
        .attr('width', size)
        .attr('height', size);


    this.display = this.svg.append("svg:g")
    // .attr("class", "container")
    // .attr("transform", "translate(" + maxLabelLength + ",0)");

    this.link = d3.svg.diagonal()
     .projection(function(d)
     {
         return [d.y, d.x];
     });


    this.display.selectAll("path.link")
     .data(this.links)
     .enter()
     .append("svg:path")
     .attr("class", "link")
     .attr("d", this.link);

    this.nodeGroup = this.display.selectAll("g.node")
     .data(this.nodes)
     .enter()
     .append("svg:g")
     .attr("class", "node")
     .attr("transform", function(d)
     {
         return "translate(" + d.y + "," + d.x + ")";
     });

    // this.nodeGroup.append("svg:circle")
    //  .attr("class", "node-dot")
    //  .attr("r", 10);

    // console.log(size)
    // this.svg = d3.select('#' + divId).append('svg')
    //     .attr('width', size)
    //     .attr('height', size);

    // this.bubble = d3.layout.pack()
    //     .size([size, size])
    //     .value(function(d) {return d.size;})
    //     .padding(3);

    // this.nodes = this.bubble.nodes(json)
    //     .filter(function(d) { return !d.children; });

    // this.visualization = this.svg.selectAll('circle').data(this.nodes);

    // this.visualization.enter().append('circle')
    //     .attr('transform', function(d) {return 'translate(' + d.x + ',' + d.y + ')'; })
    //     .attr('r', function(d) { return d.r; })
    //     .attr('class', function(d) {
    //         return d.className; 
    //     })
    //     .on('click',function(d) {
    //         circle_click_facets(this)
    //     })
    //     .style("fill", "blue")
    //     .append("title")
    //     .text(function(d) {return d.name;});

    // this.visualization.enter().append("text")
    //     .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     .attr("text-anchor", "middle")
    //     .text(function(d) {
    //         var n = d.name; 
    //         return n.replace( /[a-z ]/g, '' ); 
    //     });

    // circle_click_facets = function(e) {
    //     var index = selected_facets.indexOf(e);
    //     if (index != -1) {
    //         selected_facets.splice(index, 1);
    //     } else {
    //         selected_facets.push(e);
    //     }
    //     d3.select(e)
    //         .style("fill", get_color_facet(e))
    // }


    // this.get_selected_facets = function() {
    //     var facets = [];
    //     for (var i = 0; i < selected_facets.length; i++) {
    //         facets.push(selected_facets[i].attributes.class.value);
    //     }
    //     return facets;
    // }

    //     //returns the color of the input circle id, based on the different factors involved
    // get_color_facet = function(id) {
    //     return contains(selected_facets, id) ? "red" : "blue";
    // }

}






    // this.visualization.enter().append("title")
    //     .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d.name); return d.name;});

    // this.visualization.selectAll("g.node").append("title")
    //     // .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     // .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d); return d.name;});

