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

    exportZip = document.querySelector('#exportZip');
    exportZip.onclick = self.handleZip;
    
    borderDiv = document.querySelector('#borderSize');
    borderDiv.onchange = self.handleBorderChange;

    removeFiles = document.querySelector('#removeFiles');
    removeFiles.onclick = self.handleRemoveFiles;


    clearAll = document.querySelector('#clearAll');
    clearAll.onclick = self.handleClearAll;


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
    self.packer.sheet = self.packer.packImages(self.packer.images, self.canvasWidth, self.canvasHeight, self.border, self.postDrawHandler)
    
  }

  self.changeCanvasHeight = function(evt) {
    self.canvasHeight = evt.target.value;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
    self.packer.sheet = self.packer.packImages(self.packer.images, self.canvasWidth, self.canvasHeight, self.border, self.postDrawHandler)
    

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


    console.log(evt.dataTransfer.files);
    var handler = function() {
       var ctx = self.canvas.getContext('2d');
      ctx.drawImage(self.packer.canvas, 0, 0);

    }
    self.packer.pack(evt.dataTransfer.files, self.canvasWidth, self.canvasHeight, self.border, self.postDrawHandler);
    
   
  }
  self.postDrawHandler = function() {
       var ctx = self.canvas.getContext('2d');
      ctx.drawImage(self.packer.canvas, 0, 0);

      // Clear select box
      
       var spritesSelect = document.querySelector('#sprites');
    spritesSelect.innerHTML = '';
    for (var i = 0; i< self.packer.images.length; i++) {
      var image = self.packer.images[i];
      var option = document.createElement('option');
      option.value = image.name
      option.innerText = image.name;
      spritesSelect.appendChild(option);
    }

    }


  self.handleZip = function(evt) {
    evt.preventDefault();

    self.packer.zipFile();
  }
  self.handleBorderChange = function(evt) {
    evt.preventDefault();
    self.border = Number(evt.target.value);
    console.log(self.border);
    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
    self.packer.sheet = self.packer.packImages(self.packer.images, self.canvasWidth, self.canvasHeight, self.border, self.postDrawHandler)
    
  }

  self.handleClearAll = function(evt) {
    evt.preventDefault();
    var spritesSelect = document.querySelector('#sprites');
    spritesSelect.innerHTML = '';

    self.packer = new Pack();
    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);

  }

  self.handleRemoveFiles = function(evt) {
    evt.preventDefault();
    var values = [];
    var spritesSelect = document.querySelector('#sprites');
    for (i=0; i<spritesSelect.options.length; i++) {
      var option = spritesSelect.options[i];
      if (option.selected) {
        values.push(option.value);
      }
    }
    console.log(evt);
    console.log(values);
    var sprites = _.reject(self.packer.images, function (image) {
      for (var i = 0; i<values.length; i++) {
        if (values[i] === image.name)
          return true;
      }
      return false;
    });

    console.log(sprites);
    self.packer.images = sprites;

    self.setCanvasDimensions(self.canvasWidth, self.canvasHeight);
    self.packer.sheet = self.packer.packImages(self.packer.images, self.canvasWidth, self.canvasHeight, self.border, self.postDrawHandler);    

  }


  self.init();
}
document.addEventListener("DOMContentLoaded", function() {
  window.application = new Application();
});
