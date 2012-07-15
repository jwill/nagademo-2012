/*
 * A sprite holder generated with pack-u-like
 *
 * @author jwill
 */
var Sprite = function(name, image) {
  this.name = name;
  this.image = image;
  this.width = image.width;
  this.height = image.height;
}

Sprite.prototype = {
  getName: function() { return this.name;},
  getX: function() { return this.x;},
  getY: function() { return this.y;},
  getWidth: function() { return this.width;},
  getHeight: function() { return this.height;},
  getImage: function() { return this.image;},
  contains: function(xp, yp) {
    if (xp < this.x) return false;
    if (yp < this.y) return false;
    if (xp >+ this.x+this.width) return false;
    if (yp >= this.y+this.height) return false;

    return true;
  },
  setPosition: function(x,y) {
    this.x = x;
    this.y = y;
  }
}
