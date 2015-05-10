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
                <!--<img id="wordCloud" src="images/wordCloud.png" alt="wordCloud">-->
                <div id="title_wrapper">
                    <h1 id="title_header">Stilyagi</h1>
                    <ol id="title_definition">
                        <li id="definition_begin">(stil-<strong>yah</strong>-gee) <em>noun, plural</em></li>
                        <li>(Russian) lit. "stylish", "style hunter"</li>
                        <li>(formerly, in the Soviet Union) a person, usually young, who adopted the unconventional manner and dress of some Western youth groups, as rockers or punk-rock fans.</li>
                        <li id="definition_emphasis">a website for exploring unconventional literary styles</li>
                    </ol>
                </div>
                <h2>Explore styles of authors by following the steps below!</h1>
            </div></li>

            <li><div id="big_1" class="stacked">
                <div class="big_child step_title"> <h1> Step 1: Choose Style </h1></div>
                <div id="big_1_description" class="big_child description">
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
                <div class="big_child step_title"><h1> Step 2: Mix-ins </h1></div>
                <div id="big_2_description" class="big_child description">
                    <h4> In this step, you have the option to add the style of a particular type of text or from a particular location or time period. Use the buttons to select your choice of facet, and then follow the prompt. </h4>

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
                    <div id="label"> Click to select locations. No circles defaults to all locations.</div>
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
                    Click the bubbles to select what types of theme you would like to emulate. Hover over a bubble to see the full name of that type of theme. No selection defaults to all types.
<!--                     <select id="facets_select" multiple> </select>
 -->                </div>

            </div></li>

            <li><div id="big_3" class="stacked">
                <div class="big_child step_title"><h1> Step 3: Mood </h1></div>
                <div id="big_3_description" class="big_child description">
                    </br>
                    <h4> In this step, you can select emotions or atmospheres that you want your phrase to invoke. Click on the emoticons to choose emotions.</h4>
                </div>
                <div class="big_child">

                    <!--<div id="facet_div">-->
                        <div id="imgGrid">

                            <button class="btn" id="sad" onclick="imageClick(this.id)"> 
                                <img id="img_sad" src="images/sad.png" alt="sad">
                                <label>Sadness</label>
                            </button>

                            <button class="btn" id="happy" onclick="imageClick(this.id)"> 
                                <img id = "img_happy" src="images/happy.png" alt="happy">
                                <label>Happiness</label>
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
                                <label>Violence</label>
                            </button>

                            <button class="btn" id="fear" onclick="imageClick(this.id)"> 
                                <img id = "img_fear" src="images/fear.png" alt="fear">
                                <label>Fear</label>
                            </button>

                            <button class="btn" id="crime" onclick="imageClick(this.id)">
                                <img id = "img_crime" src="images/crime.png" alt="crime">
                                <label>Deviance</label>
                            </button>

                            <button class="btn" id="angry" onclick="imageClick(this.id)"> 
                                <img id = "img_angry" src="images/angry.png" alt="angry">
                                <label>Anger</label>
                            </button>

                            <button class="btn" id="alcohol" onclick="imageClick(this.id)"> 
                                <img id = "img_alcohol" src="images/alcohol.png" alt="alcohol">
                                <label>Inebriation</label>
                            </button>

                        </div>
                    <!--</div>-->
                </div>
            </div></li>

            <li><div id="big_4" class="stacked">
                <div class="btn btn-success" onclick="submit()" id="results_div">
                    <h1>Click here to generate a phrase</h1>
                </div>
                </br>
                <div class="invisble_div" id="show_parse" onclick="showParsed()">
                    <h4>Click here to show the sentence breakdown</h4>
                </div>
            </div> </li>

            
        </ol>
        </div>

        <div class="lightbox" id="parse_wrapper">
        <div class="lightbox_overlay" onclick="hideParsed()"></div>
        <div class="lightbox_content">
            <div id="parse_rules">
                <h1>Sentence Breakdown</h1>
                <h4>In order to understand the labels on the nodes below, please click <a target="_blank" href="http://web.mit.edu/6.863/www/PennTreebankTags.html"> here </a></h4>
            </div>
            <div id="parse_div"> 
            </div>
        </div>
        </div>


    </body>











</html>
