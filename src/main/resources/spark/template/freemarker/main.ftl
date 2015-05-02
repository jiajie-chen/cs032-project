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
                    <h1> Step 1: Choose Style </h1>
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
                    <h1> Step 2: Mix-ins </h1> 
                    </br>
                    <h4> In this step, you have the option to add the style of a particular type of text or from a particular location or time period. Use the radio buttons to select your choice of facet, and then follow the prompt. </h4>

                    <ul id="facetList">
                        <li><button class="btn" id="location" onclick="facetClick(this.id)">Facet by Location</button></li>
                        <li><button class="btn" id="time" onclick="facetClick(this.id)">Facet by Time Period</button></li>
                        <li><button class="btn" id="type" onclick="facetClick(this.id)">Facet by Themes</button></li>
                        <li><button class="btn" id="none" onclick="facetClick(this.id)">I don't want to add any more texts</button></li>
                    </ul>
                </div>

                <div class="invisible_div big_child" id = "none_child">
                    <h3>No Facets</h3>
                </div>

                <div class="invisible_div big_child" id="location_child">
                    <div id="label"> Click on the circles to select locations. Select no circles if you would like to see all locations. </br> </div>
                    <div id="map" ></div>
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
                    Click the bubbles to select what types of theme you would like to emulate. Hover over a bubble to see the full name of that type of theme. Select no types if you would like to see all types.
<!--                     <select id="facets_select" multiple> </select>
 -->                </div>

            </div></li>

            <li><div id="big_3" class="stacked">
                <div id="big_3_description" class="big_child description">
                    <h1> Step 3: Mood </h1> 
                    </br>
                    <h4> In this step, you can select emotions or themes that you want your phrase to invoke. Click on the emoticons to choose themes and emotions.</h4>
                </div>
                <div class="big_child">

                    <div id="facet_div">
                        <div id="imgGrid">

                            <button class="btn" id="sad" onclick="imageClick(this.id)"> 
                                <img id="img_sad" src="images/sad.png" alt="sad">
                                <label>Sad</label>
                            </button>

                            <button class="btn" id="happy" onclick="imageClick(this.id)"> 
                                <img id = "img_happy" src="images/happy.png" alt="happy">
                                <label>Happy</label>
                            </button>

                            <button class="btn" id="stress" onclick="imageClick(this.id)"> 
                                <img id = "img_stress" src="images/stress.png" alt="stress">
                                <label>Stress</label>
                            </button>

                            <button class="btn" id="love" onclick="imageClick(this.id)"> 
                                <img id = "img_love" src="images/love.png" alt="love">
                                <label>Love</label>
                            </button>

                            <button class="btn" id="war" onclick="imageClick(this.id)"> 
                                <img id = "img_war" src="images/war.png" alt="war">
                                <label>War</label>
                            </button>

                            <button class="btn" id="fear" onclick="imageClick(this.id)"> 
                                <img id = "img_fear" src="images/fear.png" alt="fear">
                                <label>Fear</label>
                            </button>

                            <button class="btn" id="crime" onclick="imageClick(this.id)">
                                <img id = "img_crime" src="images/crime.png" alt="crime">
                                <label>Crime</label>
                            </button>

                            <button class="btn" id="angry" onclick="imageClick(this.id)"> 
                                <img id = "img_angry" src="images/angry.png" alt="angry">
                                <label>Angry</label>
                            </button>

                            <button class="btn" id="alcohol" onclick="imageClick(this.id)"> 
                                <img id = "img_alcohol" src="images/alcohol.png" alt="alcohol">
                                <label>Alcohol</label>
                            </button>

                        </div>
                    </div> 
                </div>
            </div></li>

            <li><div id="big_4" class="stacked">
                <div id="parse_div"> </div>

                <div onclick="submit()" id="results_div">
                    Click here to generate a sentence
                </div>
            </div></li>
            
        </ol>
        </div>


    </body>











</html>
