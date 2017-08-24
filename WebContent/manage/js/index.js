var baseUrl = '../';
var token = "123";
var uid = "123";
var user = sessionStorage.getItem('user') || "";
user = user ? JSON.parse(user) : "";

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
		    }],
		    spanLeft: 5,
            spanRight: 19
		}
	},
	computed: {
        iconSize () {
            return this.spanLeft === 5 ? 14 : 24;
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
		},
		toggleClick () {
            if (this.spanLeft === 5) {
                this.spanLeft = 2;
                this.spanRight = 22;
            } else {
                this.spanLeft = 5;
                this.spanRight = 19;
            }
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
			//window.location.href = 'login.html';
		}
	}
  });

