var express = require('express');
var url = require('url');
var fs = require('fs');

var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/test', function(req, res, next) {
    fs.readFile('D:\free\game\Cuckold Homura Drunk Fuck Completed\test.mp4', function(error, data) {
        res.end(data);
    });
  });


module.exports = router;
