//Class function for the circle artist
//inspired by - http://www.d3noob.org/2014/01/tree-diagrams-in-d3js_11.html

function treeArtist(json, divId, size) {

var margin = {top: 20, right: 120, bottom: 20, left: 120},
 width = 960 - margin.right - margin.left,
 height = 500 - margin.top - margin.bottom;
 



var i = 0;

var tree = d3.layout.tree()
 .size([height, width]);

var diagonal = d3.svg.diagonal()
 .projection(function(d) { return [d.x, d.y]; });

var svg = d3.select("#" + divId).append("svg")
 .attr("width", width + margin.right + margin.left)
 .attr("height", height + margin.top + margin.bottom)
  .append("g")
 .attr("transform", "translate(" + margin.left + "," + margin.top + ")");



  // Compute the new tree layout.
  var nodes = tree.nodes(json).reverse(),
   links = tree.links(nodes);
   console.log(json);
   console.log(nodes);
   console.log(links);
  // Normalize for fixed-depth.
  nodes.forEach(function(d) { d.y = d.depth * 59; });

  // Declare the nodesâ€¦
  var node = svg.selectAll("g.node")
   .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter the nodes.
  var nodeEnter = node.enter().append("g")
   .attr("class", "node")
   .attr("transform", function(d) { 
    return "translate(" + d.x + "," + d.y + ")"; });

  nodeEnter.append("circle")
   .attr("r", 10)
   .style("fill", "#fff");

  nodeEnter.append("text")
   .attr("y", function(d) { 
    return d.children || d._children ? -13 : 13; })
   .attr("dy", ".35em")
   .attr("text-anchor", function(d) { 
    return d.children || d._children ? "end" : "start"; })
   .text(function(d) { return d.name; })
   .style("fill-opacity", 1);

  // Declare the linksâ€¦
  var link = svg.selectAll("path.link")
   .data(links, function(d) { return d.target.id; });

  // Enter the links.
  link.enter().insert("path", "g")
   .attr("class", "link")
   .attr("d", diagonal);






}


//     this.tree = d3.layout.tree()
//         .size([size, size]);


//     this.nodes = this.tree.nodes(json);
//     this.links = this.tree.links(this.nodes);
//     console.log(json)
//     console.log(this.nodes)
//     console.log(this.links)

//     this.svg = d3.select('#' + divId).append("svg:svg")
//         .attr('width', size)
//         .attr('height', size);


//     this.display = this.svg.append("svg:g").attr("transform", "translate(" + 50 + "," + 50 + ")");

//     // .attr("class", "container")
//     // .attr("transform", "translate(" + maxLabelLength + ",0)");

//     // this.link = d3.svg.diagonal()
//     //  .projection(function(d)
//     //  {
//     //      return [d.x, d.y];
//     //  });

// var margin = {top: 20, right: 120, bottom: 20, left: 120},
//  width = 960 - margin.right - margin.left,
//  height = 500 - margin.top - margin.bottom;
 

//     this.tree = d3.layout.tree()
//         .size([size, size]);

// var i = 0;

// var diagonal = d3.svg.diagonal()
//  .projection(function(d) { return [d.y, d.x]; });

//  // Normalize for fixed-depth.
//   this.nodes.forEach(function(d) { d.y = d.depth * 180; });

//   // Declare the nodesâ€¦
//   var node = this.svg.selectAll("g.node")
//    .data(this.nodes, function(d) { return d.id || (d.id = ++i); });

//   // Enter the nodes.
//   var nodeEnter = node.enter().append("g")
//    .attr("class", "node")
//    .attr("transform", function(d) { 
//     return "translate(" + d.y + "," + d.x + ")"; });

//   nodeEnter.append("circle")
//    .attr("r", 10)
//    .style("fill", "#fff");

//   nodeEnter.append("text")
//    .attr("x", function(d) { 
//     return d.children || d._children ? -13 : 13; })
//    .attr("dy", ".35em")
//    .attr("text-anchor", function(d) { 
//     return d.children || d._children ? "end" : "start"; })
//    .text(function(d) { return d.name; })
//    .style("fill-opacity", 1);

//   // Declare the linksâ€¦
//   var link = this.svg.selectAll("path.link")
//    .data(this.links, function(d) { return d.target.id; });

//   // Enter the links.
//   link.enter().insert("path", "g")
//    .attr("class", "link")
//    .attr("d", diagonal);



    // this.display.selectAll("path.link")
    //  .data(this.links)
    //  .enter()
    //  .append("svg:path")
    //  .attr("class", "link")
    //  .attr("d", this.link);







    // this.nodeGroup = this.display.selectAll("g.node")
    //  .data(this.nodes)
    //  .enter()
    //  .append("svg:g")
    //  .attr("class", "node")
    //  .attr("transform", function(d)
    //  {
    //      return "translate(" + d.y + "," + d.x + ")";
    //  });

    // this.nodeGroup.append("svg:circle")
    //     .attr("class", "node-dot")
    //     .attr("r", 2)
    //     .style("fill", "blue");

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

// }






    // this.visualization.enter().append("title")
    //     .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d.name); return d.name;});

    // this.visualization.selectAll("g.node").append("title")
    //     // .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     // .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d); return d.name;});

