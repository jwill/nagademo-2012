chrome.experimental.app.onLaunched.addListener(function() {
  chrome.appWindow.create('index.html', {
    'width': 1024,
    'height': 800
  }, function(id) {
    console.log("here");                     
    window.application = new Application();   
  });
  
});
