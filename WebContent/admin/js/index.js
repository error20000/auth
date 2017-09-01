var baseUrl = '../';
//var baseUrl = 'http://192.168.106.78:8080/palace_bbs/';
var token = "123";
var uid = "123";
var user = sessionStorage.getItem('user') || "";
user = user ? JSON.parse(user) : "";

function ajaxReq(url, param, callback, cp){
	$.ajax({
		   dataType: "json",
		   type: "POST",
		   url: url,
		   data: param,
		   success: function(data){
			   	if(data.code == -203 || data.code == -111){ // token 超时
			   		sessionStorage.removeItem('user');
			   		parent.window.location.href = "login.html";
			   	}
				if (typeof callback === "function") {
					callback(data, cp);
				}
		   },
		   error: function(data){
		   }
		});
}

new Vue({
    el: '#app',
    data: function(){
		return {
			sysName:'后台管理',
			collapsed:false,
			sysUserName: 'admin',
			sysUserAvatar: '',
			form: {
				name: '',
				region: '',
				date1: '',
				date2: '',
				delivery: false,
				type: [],
				resource: '',
				desc: ''
			},
			menuNames: [],
			authMenu: [{
		        path: '',
		        component: "",
		        name: '基础管理',
		        iconCls: 'fa fa-gear',
		        children: [
		            { path: 'area.html', component: "", name: '角色管理'},
		            { path: 'areaAD.html', component: "", name: '角色AD栏配置'},
		            { path: 'app.html', component: "", name: '应用管理'},
		            { path: 'appAD.html', component: "", name: '说说AD栏配置'}
		        ]
		    },{
		        path: '',
		        component: "",
		        name: '帖子管理',
		        iconCls: 'fa fa-gear',
		        children: [
		            { path: 'hot.html', component: "", name: '热度配置'},
		            { path: 'source.html', component: "", name: '来源配置'},
		            { path: 'article.html', component: "", name: '帖子管理'},
		            { path: 'discuss.html', component: "", name: '评论管理'},
		            { path: 'reply.html', component: "", name: '回复管理'}
		        ]
		    },{
		        path: '',
		        component: "",
		        name: '举报管理',
		        iconCls: 'fa fa-gear',
		        children: [
		            { path: 'reportArticle.html', component: "", name: '帖子举报'},
		            { path: 'reportDiscuss.html', component: "", name: '评论举报'},
		            { path: 'reportReply.html', component: "", name: '回复举报'}
		        ]
		    },{
		        path: '',
		        component: "",
		        name: '用户管理',
		        iconCls: 'fa fa-gear',
		        children: [
		        	{ path: 'user.html', component: "", name: '管理员'},
		        	{ path: 'player.html', component: "", name: '帐号信息'}
		        ]
		    },{
		        path: '',
		        component: "",
		        name: '活动管理',
		        iconCls: 'fa fa-gear',
		        children: [
		        	{ path: 'commentReward.html', component: "", name: '评论奖励活动'},
		        ]
		    },{
		        path: '',
		        component: "",
		        name: '系统管理',
		        iconCls: 'fa fa-gear',
		        children: [
		        	{ path: 'roleItf.html', component: "", name: '角色攻略'},
		        	{ path: 'welcome.html', component: "", name: '首页'}
		        ]
		    }]
		}
	},
	methods: {
		onSubmit() {
			console.log('submit!');
		},
		handleopen() {
			//console.log('handleopen');
		},
		handleclose() {
			//console.log('handleclose');
		},
		handleselect: function (a, b) {
			this.showIframe(a);
		},
		//退出登录
		logout: function () {
			var _this = this;
			this.$confirm('确认退出吗?', '提示', {
				//type: 'warning'
			}).then(() => {
				sessionStorage.removeItem('user');
				window.location.href = 'login.html';
				//日志
				if (user) {
					var logParams = {
							serverId: '',
							accountId: user.name,
							characterId: '',
							platformChannelId: '0001000600020025',
							isLogin: 0, //0：登出 1：登入
							onlineTime: 0,
							level: 0,
							vipLevel: 0
					};
					ajaxReq(baseUrl+"manage/log/login.json", logParams, function(res){});
				}
				
			}).catch(() => {

			});
		},
		//折叠导航栏
		collapse:function(){
			this.collapsed=!this.collapsed;
		},
		showMenu(i,status){
			this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
		},
		showIframe: function(index){
			var items = String(index).split("_");
			this.menuNames = []; //面包屑
			var name = "";
			var url = "";
			if(items.length == 1){
				this.menuNames.push(this.authMenu[Number(items[0])].name);
				name = this.authMenu[Number(items[0])].name;
				url = this.authMenu[Number(items[0])].path;
			}else if(items.length == 2){
				this.menuNames.push(this.authMenu[Number(items[0])].name);
				this.menuNames.push(this.authMenu[Number(items[0])].children[Number(items[1])].name);
				name = this.authMenu[Number(items[0])].children[Number(items[1])].name;
				url = this.authMenu[Number(items[0])].children[Number(items[1])].path;
			}
			$('.breadcrumb-container .title').html(name);
			$('.content-iframe').attr('src', url);
		}
	},
	mounted: function() {
		var user = sessionStorage.getItem('user');
		if (user) {
			user = JSON.parse(user);
			token = user.token || '';
			uid = user.pid || '';
			this.sysUserName = user.name;
		}else{
			window.location.href = 'login.html';
		}
	}
  });

function formatDate(d, s){
    var date = new Date();
    if(d){
        if(typeof d == 'object'){
            date = d;
        }else{
            if(isNaN(d)){
                date = new Date(d.replace(/-/g, "/").replace(/年/g, "/").replace(/月/g, "/").replace(/日/g, " ").replace(/时/g, ":").replace(/分/g, ":").replace(/秒/g, ""));
            }else{
                d = String(d).length == 10 ? d + "000" : String(d).length == 13 ? d : new Date().getTime() + Number(d);
                date = new Date(Number(d));
            }
        }
    }
    var weekday = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var weekdayS = ["日","一","二","三","四","五","六"];
    var weekdayEn = ["Sunday","Monday","Tuesday","Wednesday","Thursday ","Friday","Saturday"];
    var weekdayEnS = ["Sun.","Mon.","Tues.","Wed.","Thurs. ","Fri.","Sat."];
    var t = String(s);
    t = t.replace('yyyy', date.getFullYear());
    t = t.replace('yy', date.getYear);
    t = t.replace('MM', (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1));
    t = t.replace('M', (date.getMonth()+1));
    t = t.replace('dd', date.getDate() < 10 ? "0"+date.getDate() : date.getDate());
    t = t.replace('d', date.getDate());
    t = t.replace('HH', date.getHours() < 10 ? "0"+date.getHours() : date.getHours());
    t = t.replace('H', date.getHours());
    t = t.replace('mm', date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    t = t.replace('m', date.getMinutes());
    t = t.replace('ss', date.getSeconds() < 10 ? "0"+date.getSeconds() : date.getSeconds());
    t = t.replace('s', date.getSeconds());
    t = t.replace('S', date.getMilliseconds());
    t = t.replace('en:ww', weekdayEn[date.getDay()]);
    t = t.replace('en:w', weekdayEnS[date.getDay()]);
    t = t.replace('cn:ww', weekday[date.getDay()]);
    t = t.replace('cn:w', weekdayS[date.getDay()]);
    t = t.replace('ww', weekday[date.getDay()]);
    t = t.replace('w', weekdayS[date.getDay()]);
    return t;
};
