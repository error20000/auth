var baseUrl = parent.window.baseUrl || '../';
var token = parent.window.token || "";
var uid = parent.window.uid || "";
var user =  parent.window.user || {};
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
				//新增界面数据
				addFormVisible: false,//新增界面是否显示
				addLoading: false, //loading
				//数据
				addForm: {},
				//效验
				addFormRules: {},
				//编辑界面数据
				editFormVisible: false,//编辑界面是否显示
				editLoading: false, //loading
				//数据
				editForm: {},
				//效验
				editFormRules: {},
				//查看界面数据
				viewFormVisible: false,//编辑界面是否显示
				viewLoading: false, //loading
				//数据
				viewForm: {},
				//审核界面数据
				authFormVisible: false,//编辑界面是否显示
				authLoading: false, //loading


				//查看帖子界面数据
				artFormVisible: false,//编辑界面是否显示
				artLoading: false, //loading
				//数据
				artForm: {}
				
			}
		},
		methods: {
			formatDate: function(date){
				return parent.window.formatDate(date, 'yyyy-MM-dd HH:mm:ss');
			},
			handleSizeChange: function (val) {
				this.rows = val;
				this.getList();
			},
			handleCurrentChange: function (val) {
				this.page = val;
				this.getList();
			},
			//获取用户列表
			getList: function () {
				var self = this;
				var params = {
					page: this.page,
					rows: this.rows
				};
				this.listLoading = true;
				
				var url = baseUrl+"manage/article_report/find_paged_infos.json";
				ajaxReq(url, {rows : params.rows, pageIndex: params.page,token: token, uid: uid }, function(res){
					self.listLoading = false;
					if(res.code > 0){
						self.total = res.data.dataTotal;
						self.list = res.data.entityList;
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
				});
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗？删除后不可恢复。', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/article/delete.json";
					ajaxReq(url, {token: token, uid: uid, pid: row.pid }, function(res){
						self.listLoading = false;
						if(res.code > 0){
							self.$message({
								message: '删除成功',
								type: 'success'
							});
							self.getList();
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});
					
				}).catch(() => {
				});
			},
			//显示查看界面
			handleView: function (index, row) {
				this.viewFormVisible = true;
				this.viewForm = Object.assign({}, row);
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示界面
			handleArt: function (index, row) {
				this.artFormVisible = true;
				var self = this;
				var url = baseUrl+"manage/article/find_by_keys.json";
				ajaxReq(url, {token: token, uid: uid, pid: row.artId, isCheck: -1, isPublish: -1 }, function(res){
					if(res.code > 0){
						self.artForm = res.data[0];
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
				});
				
			},
			//删除帖子
			handleArtDel: function (index, row) {
				this.$confirm('确认删除该记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/article/update.json";
					ajaxReq(url, {token: token, uid: uid, pid: row.artId, isPublish: 2 }, function(res){
						self.listLoading = false;
						if(res.code > 0){
							self.$message({
								message: '删除成功',
								type: 'success'
							});
							self.getList();
							//日志
							ajaxReq(baseUrl+"manage/article/find_by_keys.json", {token: token, uid: uid, pid: row.artId, isPublish: -1}, function(res3){
								var artData = res3.data[0];
								if(!artData){return;}
								ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: row.artId}, function(res2){
									var str = "";
									for (var i = 0; i < res2.data.length; i++) {
										str += ","+res2.data[i].roleId;
									}
									var logParams = {
											token: token,
											uid: uid, 
											serverId: '',
											accountId: user.name,
											characterId: '',
											platformChannelId: '0001000600020025',
											operateType: 5,//5:删帖
											userType: 2,
											postId: artData.pid,
											postType: artData.style,
											gameType: artData.appId,
											characterType: str ? str.substring(1) : "",
											amount: 0,
											ex1: '',
											ex2: '',
											ex3: '',
											ex4: 'report del'
									};
									ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
								});
							});
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});
					
				}).catch(() => {
				});
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.editLoading = true;
							var url = baseUrl+"manage/article_report/update.json";
							var params = {
									token: token,
									uid: uid, 
									pid: this.editForm.pid, 
									contactWay: this.editForm.contactWay, 
									record: this.editForm.record, 
									status: 1
							};
							ajaxReq(url, params, function(res){
								self.editLoading = false;
								if(res.code > 0){
									self.$message({
										message: '提交成功',
										type: 'success'
									});
									self.editFormVisible = false;
									self.getList();
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
			this.getList();
		}
	  });
	
	
