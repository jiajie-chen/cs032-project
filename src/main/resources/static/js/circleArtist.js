//Class function for the circle artist


function circleArtist(json, divId, size) {

    var selected_author = undefined;
    this.svg = d3.select('#' + divId).append('svg')
        .attr('width', size)
        .attr('height', size);

    this.bubble = d3.layout.pack()
        .size([size, size])
        .value(function(d) {return d.size;})
        .padding(3);

    this.nodes = this.bubble.nodes(json)
        .filter(function(d) { return !d.children; });

    this.visualization = this.svg.selectAll('circle').data(this.nodes);

    this.visualization.enter().append('circle')
        .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
        .attr('r', function(d) { return d.r; })
        .attr('class', function(d) {
            return d.className; 
        })
        .on('click',function(d) {
            circle_click(this)
        })
        .style("fill", function(d) {
            if (selected_author == undefined) {selected_author = this;}
            return this == selected_author ? "red"  : "blue"
        })
        .append("title")
        .text(function(d) {return d.name;});

    this.visualization.enter().append("text")
        .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
        .attr("text-anchor", "middle")
        .style("color", "green")
        .text(function(d) {
            var n = d.name; 
            return n.replace( /[a-z ]/g, '' ); 
        });

    circle_click = function(e) {
        d3.select(selected_author)
            .style("fill", "blue");
        selected_author = e;
        d3.select(e)
            .style("fill", "red");
    }


    this.get_selected = function() {
        return selected_author.attributes.class.value;
    }

}






    // this.visualization.enter().append("title")
    //     .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d.name); return d.name;});

    // this.visualization.selectAll("g.node").append("title")
    //     // .attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; })
    //     // .attr("text-anchor", "middle")
    //     .text(function(d) {console.log(d); return d.name;});

