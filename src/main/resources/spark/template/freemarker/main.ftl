<!DOCTYPE html>
    <head>
        <meta charset="utf-8">
    
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
        <title>Stilyagi</title>
        <script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        
        <script src="js/jQuery/js/jquery-1.9.1.js"></script>
        <script src="js/jQuery/js/jquery-ui-1.10.3.custom.js"></script>
        <link href="js/jQuery/css/ui-lightness/jquery-ui-1.10.3.custom.css" rel="stylesheet">

        <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
        <script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

        <link rel="stylesheet" href="css/ui.css">
    	<link rel="stylesheet" href="css/main.css">
      	<script src = "js/main.js"></script>
      	<script src="js/circleArtist.js"></script>
        <script src="js/circleArtistMulti.js"></script>
        <script src="js/treeArtist.js"></script>

    </head>
    <body>

        <div id="wrapper" >
        <ol class="step">

            <li><div id="big_0" class="stacked">
                <img id="wordCloud" src="images/wordCloud.png" alt="wordCloud">
                <h1> Follow the instructions below to select your facets and craft a custom phrase. </h1>
            </div></li>

            <li><div id="big_1" class="stacked">
                <div id="big_1_description" class="big_child description">
                    <h1> Step 1 </h1>
                    </br>
                    <h4> In this step, select an author whose style you would like to emulate. Mouseover a bubble to see the author's full name and click on a bubble to choose an author. </h4>
                </div>
                <div id="author" class="big_child">
<!--                     <select id="author_select"> </select>
 -->            
<!--                     <div id="author_bubbles"> </div>
 -->

                </div>
            </div></li>

            <li><div id="big_2" class="stacked">
                <div id="big_2_description" class="big_child description">
                    <h1> Step 2 </h1> 
                    </br>
                    <h4> In this step, you have the option to add the style of a particular type of text or from a particular location or time period. Use the radio buttons to select your choice of facet, and then follow the prompt. </h4>
                    <div id="radioButtonDiv">
                        <input id = "location_check" type="radio" name="facets" value="location" checked="checked"  onchange="changeFacetType()">Facet by Location<br>
                        <input id = "time_check" type="radio" name="facets" value="time"  onchange="changeFacetType()">Facet by Time Period<br>
                        <input id = "type_check" type="radio" name="facets" value="type"  onchange="changeFacetType()">Facet by Text Type<br>
                        <input id = "none_check" type="radio" name="facets" value="none"  onchange="changeFacetType()">I don't want to add any more texts
                    </div>
                </div>


                <div class="invisible_div big_child" id="location_child">
                    <div id="label"> Click on the circles to select locations. Select no circles if you would like to see all locations. </br> </div>
                    <div  id="map" ></div>
                </div>

                <div class="invisible_div big_child" id = "time_child">
                    <div id="slider_div"> 
                        <div id="slider_title"> Move the slider to select a range of dates.</div>
                        </br>
                        <div id="slider"></div>
                        <div id="label_div"></div>
                    </div>
                </div>

                <div class="invisible_div big_child" id = "type_child">
                    Click the bubbles to select what types of texts you would like to emulate. Hover over a bubble to see the full name of that type of text. Select no types if you would like to see all types.
<!--                     <select id="facets_select" multiple> </select>
 -->                </div>

            </div></li>

            <li><div id="big_3" class="stacked">
                <div id="big_3_description" class="big_child description">
                    <h1> Step 3 </h1> 
                    </br>
                    <h4> In this step, you can select emotions or themes that you want your phrase to invoke. Click on the emoticons to choose themes and emotions.</h4>
                </div>
                <div class="big_child">

                    <div id="facet_div">
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
                </div>
            </div></li>

            <li><div id="big_4" class="stacked">
                <div class="big_child description" onclick="submit()" id="results_div">
                    <h1>Click here to generate a sentence</h1>
                </div>

            </div></li>

            <li> <div id="big_5" class="stacked">

                <div id="parse_div"> 
                    <h1>The tree will go here</h1>
                </div>

            </div> </li>

            
        </ol>
        </div>


    </body>











</html>
