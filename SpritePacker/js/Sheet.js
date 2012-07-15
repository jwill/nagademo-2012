/*
 * A sprite sheet generated with pack-u-like
 *
 * @author jwill
 */

var Sheet = function(imageData, images, defFile, filename) {
  this.image = imageData;
  this.sprites = images;
  this.filename = filename;
  this.spriteData = defFile;
}

Sheet.prototype = {
  getImage: function() {
    return this.image;
  },
  getSprites: function() {
    return this.sprites;
  },
  setFilename: function(f) {
    return this.filename = f;
  },
  getFilename: function() {
    return this.filename;
  }
}
