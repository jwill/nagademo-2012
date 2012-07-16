/**
 * Packer for images
 *
 * @author jwill
 */
var Pack = function() {
  this.images = [];
}

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
  pack: function(files, width, height, border, handler) {
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
            self.images.push(sprite);
            self.sheet = self.packImages(self.images, width, height, border, handler)
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
  packImages: function(images, width, height, border, handler) {
    var self = this;
    var result;
    var sortedImages = _.sortBy(images,'height');
    var x = 0, y = 0, rowHeight = 0;

    var sheetData = [];

    self.canvas = document.createElement("canvas");
    self.canvas.width = width;
    self.canvas.height = height;
    
    var ctx = self.canvas.getContext("2d");

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
      handler();

    }
    return new Sheet(self.canvas.toDataURL(), images, sheetData);

  },
  zipFile: function() {
    var zip = new JSZip();
    zip.file("sheet.def.txt", JSON.stringify(this.sheet.spriteData));
    
    // JSZip likes to append this data itself
    var tempImage = this.sheet.image.replace("data:image/png;base64,", "");
    console.log(tempImage)
    zip.file("sheet.png", tempImage, {base64:true});
   
    content = zip.generate();
    
    // Run this if just running the HTML file outside of an extension
    //location.href = "data:application/zip;base64," + content;
    //
    // Packaged app
    chrome.fileSystem.chooseFile({type: 'saveFile'}, function(writableFileEntry) {
      writableFileEntry.createWriter(function(writer) {
        writer.onerror = function(err) {console.log(err)};
        writer.onwriteend = function(e) {
          var notification = webkitNotifications.createNotification('', '',  'File saved.');
          notification.show();
        };
        // write the bytes of the string to an ArrayBuffer
        var raw = atob(content);
        var ab = new ArrayBuffer(content.length);
        var uInt8Array = new Uint8Array(raw.length);
        for (var i = 0; i < raw.length; i++) {
            uInt8Array[i] = raw.charCodeAt(i);
        }
        writer.write(new Blob([uInt8Array], {type:'application/zip'}));
      },  function(err) {console.log(err)});

    });
  }
}
