<!DOCTYPE html>

    <head>
        <meta charset="utf-8">
    
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
        <title>Project</title>
        <script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        
        <script src="js/jQuery/js/jquery-1.9.1.js"></script>
        <script src="js/jQuery/js/jquery-ui-1.10.3.custom.js"></script>
        <link href="js/jQuery/css/ui-lightness/jquery-ui-1.10.3.custom.css" rel="stylesheet">

        <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
        <script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>

    	<link rel="stylesheet" href="css/main.css">
      	<script src = "js/main.js"></script>
      	
    </head>
    <body>

        <div id="wrapper" >
            <div id="label"> Click a Circle to Select a Location </div>

            <div id="author">
                <div id="author_title"> Select an Author</div>
                <select id="author_select">        
                </select>
            </div>

            <div id="map" ></div>

            <div id="slider_div"> 
                <div id="slider_title"> Select the Range of dates</br></div>
                <div id="slider"></div>
                <div id="label_div"></div>
            </div> 

            <div id="facet_div"> Sentence facets will go here!
                <div id="imgGrid">

                    <button id="sad" onclick="imageClick(this.id )"> 
                        <img id="img_sad" src="images/sad.png" alt="sad">
                    </button>

                    <button id="h" onclick="imageClick(this.id )"> 
                        <img id = "img_h" src="images/sad.png" alt="sad">
                    </button>

                    <button id="a" onclick="imageClick(this.id )"> 
                        <img id = "img_a" src="images/sad.png" alt="sad">
                    </button>

                    <button id="b" onclick="imageClick(this.id )"> 
                        <img id = "img_b" src="images/sad.png" alt="sad">
                    </button>

                    <button id="c" onclick="imageClick(this.id )"> 
                        <img id = "img_c" src="images/sad.png" alt="sad">
                    </button>

                    <button id="d" onclick="imageClick(this.id )"> 
                        <img id = "img_d" src="images/sad.png" alt="sad">
                    </button>

                    <button id="e" onclick="imageClick(this.id )"> 
                        <img id = "img_e" src="images/sad.png" alt="sad">
                    </button>

                    <button id="f" onclick="imageClick(this.id )"> 
                        <img id = "img_f" src="images/sad.png" alt="sad">
                    </button>

                    <button id="g" onclick="imageClick(this.id )"> 
                        <img id = "img_g" src="images/sad.png" alt="sad">
                    </button>

                </div>
            </div> 

            <div id="submit_div"> 
                <button id = "submit_button" onclick="submit()"> Generate Sentence! </button>
            </div>

            <div id="results_div">
            </div>

        </div>


    </body>











</html>
