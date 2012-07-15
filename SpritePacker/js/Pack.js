/**
 * Packer for images
 *
 * @author jwill
 */
var Pack = function() { }

Pack.prototype = {
  /**
   * Packs the provided images
   *
   * @param files The list of file objects
   * @param width The width of the generated sheet
   * @param height The height of the generated sheet
   * @param border The The size of the border between sprites
   * @param outFile The generated sprite sheet
   */ 
  pack: function(files, width, height, border) {
    var images = [];
    
    for (var i = 0; i<files.length; i++) {
      var f = files[i];
      // Only process image files
      if (!f.type.match('image.*'))
        continue;
  
      var reader = new FileReader();
      var self= this;

      // Get file information and load into Sprites
      reader.onload = (function(theFile) {
        return function(e) {
          // Load data into image file
          var image = new Image();
          image.src = e.target.result;
          image.onload = function() {
            
            var sprite = new Sprite(theFile.name, this);
            images.push(sprite);
            console.log(images.length);
            if (files.length == images.length) {
              console.log("here");
              self.sheet = self.packImages(images, width, height, border)

            }
          };

        }
      })(f);
      // Read image file as data URL.
      reader.readAsDataURL(f);
      
    }
      },
  /**
   * Packs the provided images
   *
   * @param images The list of image objects
   * @param width The width of the generated sheet
   * @param height The height of the generated sheet
   * @param border The The size of the border between sprites
   * @param outFile The generated sprite sheet
   */ 
  packImages: function(images, width, height, border) {
    var result;
    var sortedImages = _.sortBy(images,'height');
    var x = 0, y = 0, rowHeight = 0;

    var sheetData = [];

    var canvas = document.createElement("canvas");
    canvas.width = width;
    canvas.height = height;
    
    var ctx = canvas.getContext("2d");

    for (var i = 0; i<images.length; i++) {
      var currentSprite = images[i];
      if (x + currentSprite.width > width) {
        x = 0;
        y += rowHeight;
      }

      if (rowHeight == 0) {
        rowHeight = currentSprite.height + border;
      }

      // write sprite data
      var data = {};
      data.name = currentSprite.name;
      data.x = x;
      data.y = y;
      data.width = currentSprite.width;
      data.height = currentSprite.height;

      currentSprite.setPosition(x,y);
      ctx.drawImage(currentSprite.getImage(), x, y);
      x += currentSprite.width + border;
      sheetData.push(data);

    }
    return new Sheet(canvas.toDataURL(), images, sheetData);

  },
  zipFile: function() {
    var zip = new JSZip();
    zip.file("sheet.def.txt", JSON.stringify(this.sheet.spriteData));
    
    // JSZip likes to append this data itself
    var tempImage = this.sheet.image.replace("data:image/png;base64,", "");
    console.log(tempImage)
    zip.file("sheet.png", tempImage, {base64:true});
   
    content = zip.generate();
    location.href = "data:application/zip;base64," + content;
  }
}
