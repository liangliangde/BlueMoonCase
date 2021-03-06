<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">

  <!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]--><!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]--><!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
  <!--[if gt IE 8]><!--><!--<![endif]-->
  <meta name="robots" content="noindex">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title></title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">
  <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
  <!-- <link rel="stylesheet" href="css/normalize.css">
   <link rel="stylesheet" href="css/main.css">
   <link rel="stylesheet" href="css/RadSet.css">
   <link rel="stylesheet" href="css/layout-default-latest.css">
   <link rel="stylesheet" href="css/complex.css">
   <link rel="sytlesheet" type="text/css" href="css/jquery-ui-1.10.2.custom.min.css"/>
   <script src="js/vendor/modernizr-2.6.2.min.js"></script>
    -->
  <link rel="stylesheet" href="css/layout-default-latest.css">
  <link rel="stylesheet" href="css/complex.css">
  <link rel="stylesheet" href="css/arealine.css">
  <style>
    body
    {
      font: 15px sans-serif;
    }

    .chord path
    {
      fill-opacity: .67;
      stroke: #000;
      stroke-width: .5px;
    }

    .ui-dialog-title {
      color: #000000;
    }

  </style>
  <style>

    circle,
    path {
      cursor: pointer;
    }

    circle {
      fill: none;
      pointer-events: all;
    }

    .tooltip { background-color: white;
      padding: 3px 5px;
      border: 1px solid black;
      text-align: center;}

    html {
      font-family: cursive;
    }

  </style>
  <style>
    .yaxis path,
    .yaxis line {
      fill: none;
      stroke: #000;
      shape-rendering: crispEdges;
    }
    .xaxis path {
      display: none;
    }
    .bar {
      fill: steelblue;
    }
  </style>
  <style>
    #chart{
    //text-align: center;
      width: 400px;
      height: 350px;

    }
  </style>
</head>
<body>
<!--[if lt IE 7]>

<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
-->
<![endif]-->
<!-- Add your site or application content here --><!-- <p>Hello world! This is HTML5 Boilerplate.</p> -->
<!--
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script><!--<script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
<script src="js/jquery-ui-1.10.2.custom.min.js"></script>
<script src="js/plugins.js"></script>
<script src="js/main.js"></script>
<script src="jquery.layout-latest.js"></script>
<script src="js/d3.v3.js"></script>
<script src="js/jquery.arrayUtilities.min.js"></script><!--<script src="js/bit-array.js"></script>
<script src="js/vendor/jquery.tablesorter.min.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="//d3js.org/d3.v3.min.js"></script>-->
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="jquery-ui-1.10.2.custom.min.js"></script>
<script src="js/jquery.layout-latest.js"></script>
<script src="js/d3.js"></script>


<div class="ui-layout-west">
  <div class="header">不同规格销量对比</div>
  <div class="content" style="height: 80%;">

    <p id="menu">品种选择:
      <select>
        <option value="A">洗衣液</option>
        <option value="B">洗手液</option>
        <option value="C">Urban population (% of total)</option>
        <option value="D">Population, total (,000,000)</option>
        <option value="E">Mortality rate, infant (per 1,000 live births)</option>
        <option value="F">Life expectancy at birth, total (years)</option>

      </select>
    <div id="BrandBar">
    </div>
    <div>Movies by #Genres</div>
    <ul id="elementsByDegree" class="DegreeList">
    </ul>
  </div>
</div>
<div class="ui-layout-east">
  <div class="ui-layout-center">
    <div class="header">特性对比</div>
    <div class="content">
      <input id="click_1" type="radio" name="sel" value="true" checked="true">Length representation</input>
      <input id="click_2" type="radio" name="sel" value="false">Angle representation</input>

      <div id='chart' ></div>
    </div>
  </div>
  <!--    <div class="ui-layout-south">
              <div class="header">Show itemset of Size:</div>
              <div class="content">
                  <table id="tabItemsets" border="1" class="tablesorter">
                  </table>
              </div>
          </div>
          -->
</div>
<!--
    <div class="ui-layout-south">
        <div class="header">Log</div>
        <div class="content">
            <ul id="log" class="log">
            </ul>
        </div>
    </div>
-->
<div id="mainContent">
  <div class="header">front view</div>
  <div class="content"> <a id="reload" href="#">waiting</a>&nbsp;&nbsp;
    <div id="radialset">  </div>
    <div id="arealine"></div>
  </div>
  <!--   <div class="footer">

     </div>
 -->
</div>

<script>

  var outerLayout, innerLayout;


  var showConsole = (localStorage.getItem("consoleShow") === "true" ? true : false);

  var layoutSettings_Outer = {
    name: "outerLayout"
    , defaults: {
      size: "auto"
      , minSize: 50
      , paneClass: "pane"    // default = 'ui-layout-pane'
      , resizerClass: "resizer" // default = 'ui-layout-resizer'
      , togglerClass: "toggler" // default = 'ui-layout-toggler'
      , buttonClass: "button"  // default = 'ui-layout-button'
      , contentSelector: ".content"  // inner div to auto-size so only it scrolls, not the entire pane!
      , contentIgnoreSelector: "span"    // 'paneSelector' for content to 'ignore' when measuring room for content
      , togglerLength_open: 35      // WIDTH of toggler on north/south edges - HEIGHT on east/west edges
      , togglerLength_closed: 35      // "100%" OR -1 = full height
      , hideTogglerOnSlide: true    // hide the toggler when pane is 'slid open'
      , togglerTip_open: "Close This Pane"
      , togglerTip_closed: "Open This Pane"
      , resizerTip: "Resize This Pane"
      //  effect defaults - overridden on some panes
      , fxName: "slide"   // none, slide, drop, scale
      , fxSpeed_open: 750
      , fxSpeed_close: 1500
      , fxSettings_open: {easing: "easeInQuint"}
      , fxSettings_close: {easing: "easeOutQuint"}
    }
    , west: {
      size: 570
      , spacing_closed: 21      // wider space when closed
      , togglerLength_closed: 21      // make toggler 'square' - 21x21
      , togglerAlign_closed: "top"   // align to top of resizer
      , togglerLength_open: 0     // NONE - using custom togglers INSIDE west-pane
      , togglerTip_open: "Close West Pane"
      , togglerTip_closed: "Open West Pane"
      , resizerTip_open: "Resize West Pane"
      , slideTrigger_open: "click"   // default
      , initClosed: false
      , slidable: true
      //  add 'bounce' option to default 'slide' effect
      , fxSettings_open: {easing: ""}
    }
    , east: {
      size: 450
      , spacing_closed: 21      // wider space when closed
      , togglerLength_closed: 21      // make toggler 'square' - 21x21
      , togglerAlign_closed: "top"   // align to top of resizer
      , togglerLength_open: 0       // NONE - using custom togglers INSIDE east-pane
      , togglerTip_open: "Close East Pane"
      , togglerTip_closed: "Open East Pane"
      , resizerTip_open: "Resize East Pane"
      , slideTrigger_open: "click"
      , initClosed: false
      , slidable: true
      //  override default effect, speed, and settings
      , fxName: "drop"
      , fxSpeed: "normal"
      , fxSettings: {easing: ""} // nullify default easing
      , childOptions: {
        south: {
          size: 300
        }
      }
    }
    , south: {
      maxSize: 200
      , minSize: 200
      , spacing_closed: 0     // HIDE resizer & toggler when 'closed'
      , slidable: true   // REFERENCE - cannot slide if spacing_closed = 0
      , initClosed: true
    }

    , center: {
      paneSelector: "#mainContent"      // sample: use an ID to select pane instead of a class
      , minWidth: 200
      , minHeight: 200
    }
  };

  outerLayout = $("body").layout(layoutSettings_Outer);


  var westSelector = "body > .ui-layout-west"; // outer-west pane
  var eastSelector = "body > .ui-layout-east"; // outer-east pane

  $("<span></span>").addClass("pin-button").prependTo(westSelector);
  $("<span></span>").addClass("pin-button").prependTo(eastSelector);

  outerLayout.addPinBtn(westSelector + " .pin-button", "west");
  outerLayout.addPinBtn(eastSelector + " .pin-button", "east");


  $("<span></span>").attr("id", "west-closer").prependTo(westSelector);
  $("<span></span>").attr("id", "east-closer").prependTo(eastSelector);

  outerLayout.addCloseBtn("#west-closer", "west");
  outerLayout.addCloseBtn("#east-closer", "east");

  //westLayout = $(divWestCont).layout();

  // DEMO HELPER: prevent hyperlinks from reloading page when a 'base.href' is set
  $("a").each(function() {
    var path = document.location.href;
    if (path.substr(path.length - 1) == "#")
      path = path.substr(0, path.length - 1);
    if (this.href.substr(this.href.length - 1) == "#")
      this.href = path + "#";
  });

</script>
<script src="js/sunburst.js"></script>

<script src="js/stackline.js"></script>

<script src="js/cluster.js"></script>
<script src="js/BrandBar.js"> </script>
<!--


<script src="js/arealine.js"></script>

-->
</body>
</html>