$(document).ready(function() {

  var app = new Vue({
    el: '#app',
    data: {
      console: '',
      input: ''
    },
    methods: {
      submit: function() {

        if (app.input != '') {
          app.console+=app.input + "<br><br>";
          $.post("/j/users/", { input: app.input }, function(data) {
              app.console+=data + "<br>";
              app.input='';
          });
        }
        //app.console=$.get("/j/users/", {input: app.input});
      }
    }
  });
  if (app.console == '') {
    app.console+="Welcome to Zombie Survival!<br><br><br>" +
    "Type \"characters\" for a character list.<br>" +
    "Type \"stats\" for character info.<br>" +
    "Type \"select\" to select character.<br>";
  }
});
