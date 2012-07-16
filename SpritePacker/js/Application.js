var Application = function() {
  var self = this;
  
  self.canvasWidth = 256;
  self.canvasHeight = 256;
  self.border = 0;
  self.packer = new Pack();
  
  
 

  self.init =  function() {
    self.canvas = document.querySelector('#canvas');
    sheetWidthDiv = document.querySelector('#sheetWidth');
    sheetWidthDiv.onchange = self.changeCanvasWidth;

    sheetHeightDiv = document.querySelector('#sheetHeight');
    sheetHeightDiv.onchange = self.changeCanvasHeight;
    
    dropZone = document.querySelector('.spriteSheet');
    dropZone.ondrop = self.handleDrop;
    
    
    self.initCanvas();
  }

  self.setCanvasDimensions = function(w, h) {
    var c = document.querySelector('canvas');
    self.canvasWidth = w;
    self.canvasHeight = h;

    c.width = self.canvasWidth;
    c.height = self.canvasHeight;
    self.initCanvas();
  }

  self.changeCanvasWidth = function(evt) {
    self.canvasWidth = evt.target.value;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
  }

  self.changeCanvasHeight = function(evt) {
    self.canvasHeight = evt.target.value;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
  }

  self.initCanvas = function() {
    var pattern = document.createElement('canvas');
    pattern.width = pattern.height = 40;
    
    var pctx = pattern.getContext('2d');
    pctx.fillStyle = 'rgb(177,177,177)';
    pctx.fillRect(0,0,20,20);
    pctx.fillRect(20,20,20,20);
  
    var ctx = self.canvas.getContext('2d');
    var tempPattern = ctx.createPattern(pattern, 'repeat');
    ctx.fillStyle = tempPattern;
    ctx.fillRect(0,0, self.canvasWidth, self.canvasHeight);
  }

  self.handleDrop = function(evt) {
    evt.preventDefault();

    console.log('dropped');
    
    console.log(evt.dataTransfer.files);
    var handler = function() {
       var ctx = self.canvas.getContext('2d');
      ctx.drawImage(self.packer.canvas, 0, 0);

    }
    self.packer.pack(evt.dataTransfer.files, self.canvasWidth, self.canvasHeight, self.border, handler);
    
       
  }


  self.init();
}

