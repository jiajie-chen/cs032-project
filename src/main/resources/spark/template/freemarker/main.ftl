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

            <div id="facet_div"> Click on the themes that you want to appear in your phrase!
                <div id="imgGrid">

                    <button id="sad" onclick="imageClick(this.id)"> 
                        <img id="img_sad" src="images/sad.png" alt="sad">
                    </button>

                    <button id="happy" onclick="imageClick(this.id)"> 
                        <img id = "img_happy" src="images/happy.png" alt="happy">
                    </button>

                    <button id="stress" onclick="imageClick(this.id)"> 
                        <img id = "img_stress" src="images/stress.png" alt="stress">
                    </button>

                    <button id="love" onclick="imageClick(this.id)"> 
                        <img id = "img_love" src="images/love.png" alt="love">
                    </button>

                    <button id="war" onclick="imageClick(this.id)"> 
                        <img id = "img_war" src="images/war.png" alt="war">
                    </button>

                    <button id="fear" onclick="imageClick(this.id)"> 
                        <img id = "img_fear" src="images/fear.png" alt="fear">
                    </button>

                    <button id="crime" onclick="imageClick(this.id)"> 
                        <img id = "img_crime" src="images/crime.png" alt="crime">
                    </button>

                    <button id="angry" onclick="imageClick(this.id)"> 
                        <img id = "img_angry" src="images/angry.png" alt="angry">
                    </button>

                    <button id="alcohol" onclick="imageClick(this.id)"> 
                        <img id = "img_alcohol" src="images/alcohol.png" alt="alcohol">
                    </button>

                </div>
            </div> 

<!--             <div id="submit_div"> 
                <button id = "submit_button" onclick="submit()"> Generate a Phrase! </button>
            </div> -->

            <div onclick="submit()" id="results_div">
                Click here to generate a sentence
            </div>

        </div>


    </body>











</html>
