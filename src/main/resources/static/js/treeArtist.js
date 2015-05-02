//Class function for the circle artist
//inspired by - http://www.d3noob.org/2014/01/this.tree-diagrams-in-d3js_11.html

function treeArtist(json, divId, size) {

// var margin = {top: 20, right: 120, bottom: 20, left: 120},
//  width = 960 - margin.right - margin.left,
//  height = 500 - margin.top - margin.bottom;
 



    var counter = 0;

    this.tree = d3.layout.tree()
     .size([1000, 3000]);

    this.svg = d3.select("#" + divId).append("svg")
        .attr("width", 1500)
        .attr("height", 3000)
        .append("g")
        .attr("transform", "translate(" + 100 + "," + 200 + ")");


    this.nodes = this.tree.nodes(json).reverse(),
    this.nodes.forEach(function(d) { d.y = d.depth * 100; });
    this.links = this.tree.links(this.nodes);

    this.node = this.svg.selectAll("g.node")
       .data(this.nodes, function(d) {
            d.id = counter
            counter ++;
            return counter; 
        });

       // Enter the this.nodes.
    this.nodeEnter = this.node.enter().append("g")
        .attr("class", "node")
        .attr("transform", function(d) { 
            return "translate(" + d.x + "," + d.y + ")";
        });

    this.nodeEnter.append("circle")
       .attr("r", 10)
       .style("fill", "#fff");

    this.nodeEnter.append("text")
       .attr("y", function(d) { 
            return d.children ? -15 : 15; 
        })
       //.attr("dy", ".35em")
       .attr("text-anchor", function(d) { 
            return d.children ? "end" : "start";0
        })
       .text(function(d) { return d.name; })
       .style("fill-opacity", 1);

    this.link = this.svg.selectAll("path.link").data(this.links, function(d) { return d.target.id; });

    this.link.enter().insert("path", "g")
        .attr("class", "link")
        .attr("d", d3.svg.diagonal()
        .projection(function(d) { return [d.x, d.y]; }));






}

