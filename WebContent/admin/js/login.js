var baseUrl = '../';
//var baseUrl = 'http://192.168.106.78:8080/palace_bbs/';

function ajaxReq(url, param, callback, cp){
	$.ajax({
		   dataType: "json",
		   type: "POST",
		   url: url,
		   data: param,
		   success: function(data){
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
      data: function() {
        return {
            logining: false,
            ruleForm2: {
              username: '',
              password: ''
            },
            rules2: {
              username: [
                { required: true, message: '请输入账号', trigger: 'blur' },
                //{ validator: validaePass }
              ],
              password: [
                { required: true, message: '请输入密码', trigger: 'blur' },
                //{ validator: validaePass2 }
              ]
            },
            checked: true
          }
      },
      methods: {
          handleReset2: function() {
            this.$refs.ruleForm2.resetFields();
          },
          handleSubmit2: function(ev) {
            var _this = this;
            this.$refs.ruleForm2.validate((valid) => {
              if (valid) {
                var self = this;
				this.logining = true;
				var url = baseUrl+"manage/user/login.json";
				var params = {
						name: this.ruleForm2.username,
						pwd: this.ruleForm2.password
				};
				ajaxReq(url, params, function(res){
					self.logining = false;
					if(res.code > 0 && res.data.length > 0){
						sessionStorage.setItem('checked', self.checked);
						sessionStorage.setItem('user', JSON.stringify(res.data[0]));
						sessionStorage.setItem('loginTime', new Date().getTime());
						window.location.href = 'index.html';
						//日志
						var logParams = {
								serverId: '',
								accountId: res.data[0].name,
								characterId: '',
								platformChannelId: '0001000600020025',
								isLogin: 1, //0：登出 1：登入
								onlineTime: 0,
								level: 0,
								vipLevel: 0
						};
						ajaxReq(baseUrl+"manage/log/login.json", logParams, function(res){});
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
				});
              } else {
                console.log('error submit!!');
                return false;
              }
            });
          }
        },
    	mounted: function() {
    		var user = sessionStorage.getItem('user');
    		var checked = sessionStorage.getItem('checked');
    		if (user && checked == 'true') {
    			//window.location.href = 'index.html';
    		}
    	}
    });

