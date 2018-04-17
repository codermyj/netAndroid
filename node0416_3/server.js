const http = require('http');
const fs = require('fs');
const querystring = require('querystring');
const urlLib = require('url');

var users = {}

var server = http.createServer(function(req, res){
	var str = "";

	req.on('data', function(data){
		str += data;
	});

	req.on('end', function(){
		var obj = urlLib.parse(req.url, true);
		const url = obj.pathname;
		const GET = obj.query;
		const POST = querystring.parse(str);

		if(url == '/user'){
			switch(GET.act){
				case 'reg':
					if(users[GET.user]){
						res.write("{'ok': false, 'msg': '该用户名已存在!'}");
					}else{
						users[GET.user] = GET.pass;
						res.write("{'ok': true, 'msg':'注册成功!'}");
					}
					break;
				case 'login':
					if(users[GET.user] == null){
						res.write("{'ok': false, 'msg':'用户不存在!'}");
					}else if(users[GET.user] != GET.pass){
						res.write("{'ok': false, 'msg': '用户名或密码错误'}");
					}else{
						res.write("{'ok': true, 'msg': '登录成功'}");
					}
					break;
				default:
					res.write("{'ok': false, 'msg': '未知的act'}");
			}
			res.end();

		}else{
			var fileName = "./www" + url;
			fs.readFile(fileName, function(err, data){
			if(err){
				res.write('404');
			}else{
				res.write(data);
			}
			res.end();
			});
		}
		
	});

});

server.listen(8080);