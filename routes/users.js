var express = require('express')
var router = express.Router()
var test = require('../test.js')

/* GET users listing. */
router.get('/', function(req, res) {
  var text = req.param('input')
  res.send(text)
});

router.post('/', function(req, res) {
  console.log(req.body.input)
  var t = req.body.input
  var j = test.tell(t)
  res.send(j)
});

module.exports = router;
