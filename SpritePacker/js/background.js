chrome.app.runtime.onLaunched.addListener(function() {
  chrome.app.window.create('index.html', {
    'width': 1024,
    'height': 800
  }, function(id) {
    window.application = new Application();   
  });
  
});
