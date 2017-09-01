var baseUrl = parent.window.baseUrl || '../';
var token = parent.window.token || "";
var uid = parent.window.uid || "";

var ajaxReq = parent.window.ajaxReq || "";


	new Vue({
	    el: '#app',
	    data: function(){
	    	return {
				filters: {
					name: ''
				},
				list: [],
				total: 0,
				page: 1,
				rows: 10,
				listLoading: false,
				sels: [],//列表选中列
				//cdn
				cdnForm: {
					autoRefresh: 0,
					autoRefreshTime: 1200
				},
				cdnFormRules: {
					autoRefresh: [
			            { type: 'number', message: '请至少选择一个状态', trigger: 'change' }
			          ],
			        autoRefreshTime: [
						{ validator: (rule, value, callback) => {
					          if (!Number(value)) {
					            callback(new Error('请输入自动刷新频率，单位秒'));
					          } else {
					        	  if(Number(value) < 1200){
					        		  this.cdnForm.autoRefreshTime =  1200;
					        	  }
					            callback();
					          }
						}, trigger: 'blur' }
		            ]
				},
				cdnLoading: false, 
				//审核
				authForm: {},
				authFormRules: {},
				authLoading: false,
				//默认
				defultCdnForm: {},
				defultAuthForm: {}
				
				
			}
		},
		methods: {
			formatDate: function(date){
				return parent.window.formatDate(date, 'yyyy-MM-dd HH:mm:ss');
			},
			//查询
			getCDNConfig: function () {
				var self = this;
				
				var url = baseUrl+"manage/cdn/config.json";
				ajaxReq(url, {token: token, uid: uid }, function(res){
					if(res.code > 0){
						self.cdnForm = res.data;
						self.defultCdnForm =  Object.assign({}, res.data);
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
				});
			},
			//查询
			getAuthConfig: function () {
				var self = this;
				
				var url = baseUrl+"manage/check_config/find_by_keys.json";
				ajaxReq(url, {token: token, uid: uid }, function(res){
					if(res.code > 0){
						self.authForm = res.data;
						self.defultAuthForm = Object.assign({}, res.data);
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
				});
			},
			//新增
			cdnSubmit: function () {
				this.$refs.cdnForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var params = Object.assign({}, this.cdnForm);
							params.token = token;
							params.uid = uid;
							var self = this;
							this.cdnLoading = true;
							
							var url = baseUrl+"manage/cdn/update.json";
							ajaxReq(url, params, function(res){
								self.cdnLoading = false;
								if(res.code > 0){
									self.$message({
										message: '提交成功',
										type: 'success'
									});
									self.getCDNConfig();
								}else{
									self.$message({
										message: res.msg,
										type: 'warning'
									})
								}
							});
						});
					}
				});
			},
			//编辑
			authSubmit: function () {
				this.$refs.authForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.authLoading = true;
							var url = baseUrl+"manage/check_config/update.json";
							var params = {};
							params.token = token;
							params.uid = uid;
							params.pid = this.authForm.pid;
							params.needCheck = this.authForm.needCheck;
							ajaxReq(url, params, function(res){
								self.authLoading = false;
								if(res.code > 0){
									self.$message({
										message: '提交成功',
										type: 'success'
									});
									self.getAuthConfig();
								}else{
									self.$message({
										message: res.msg,
										type: 'warning'
									})
								}
							});
							
						});
					}
				});
			},
			cdnCancel: function () {
				this.cdnForm = Object.assign({}, this.defultCdnForm);
			},
			authCancel: function () {
				this.authForm = Object.assign({}, this.defultAuthForm);
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var pids = this.sels.map(item => item.pid).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					var params = { pids: pids };
					batchRemove(params).then((res) => {
						this.listLoading = false;
						if(res.code > 0){
							//NProgress.done();
							this.$message({
								message: '删除成功',
								type: 'success'
							});
							this.getList();
						}else{
							this.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});
				}).catch(() => {
				});
			}
		},
		mounted: function() {
			this.getCDNConfig();
			this.getAuthConfig();
		}
	  });
	
