var Application = function() {
  var self = this;
  
  self.canvasWidth = 256;
  self.canvasHeight = 256;
  
  
 

  self.init =  function() {
    sheetWidthDiv = document.querySelector('#sheetWidth');
    sheetWidthDiv.onchange = self.changeCanvasWidth;
    sheetHeightDiv = document.querySelector('#sheetHeight');
    sheetHeightDiv.onchange = self.changeCanvasHeight;

  }

  self.setCanvasDimensions = function(w, h) {
    var c = document.querySelector('canvas');
    self.canvasWidth = w;
    self.canvasHeight = h;

    c.width = self.canvasWidth;
    c.height = self.canvasHeight;
  }

  self.changeCanvasWidth = function(evt) {
    self.canvasWidth = evt.target.value;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
  }

  self.changeCanvasHeight = function(evt) {
    self.canvasHeight = evt.target.value;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
  }


  self.init();
}

