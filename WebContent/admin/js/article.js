var baseUrl = parent.window.baseUrl || '../';
var token = parent.window.token || "";
var uid = parent.window.uid || "";
var user =  parent.window.user || {};
var ajaxReq = parent.window.ajaxReq || "";

Vue.use(VueQuillEditor);

var myvue =	new Vue({
	    el: '#app',
	    data: function(){
	    	return {
				filters: {
					pid: '',
					type: '',
					appId: '',
					start: '',
					end: ''
					
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
				editForm: {
					isComment: 0,
					isTop: 0,
					isLight: 0,
					isRecommend: 0
				},
				editCacheForm: {},
				//效验
				editFormRules: {
					appId: [
						{ required: true, message: '请输入ID', trigger: 'blur' }
					],
					name: [
						{ required: true, message: '请输入名称', trigger: 'blur' }
					]
				},
				//查看界面数据
				viewFormVisible: false,//编辑界面是否显示
				viewLoading: false, //loading
				//数据
				viewForm: {},
				//审核界面数据
				authFormVisible: false,//编辑界面是否显示
				authLoading: false, //loading

				topLoading: false,
				topFormVisible: false,
				topForm: {
					type: 0,
					sortId: 0
				},
				indeterminate: false,
				items: [],
				checkedItems: [],
				checkAll: false,
				
				articleFormVisible: false,
				articleForm: {},
				articleFormRules: {
					pid: [
						{ required: true, message: '请输入帖子ID', trigger: 'blur' }
					],
					authorId: [
						{ required: true, message: '请输入作者', trigger: 'blur' }
					],
					title: [
						{ required: true, message: '请输入帖子标题', trigger: 'blur' }
					],
					content: [
						{ required: true, message: '请输入帖子内容', trigger: 'blur' }
					]},
				articleLoading: false,
				appsData: [],
				rolesData: [],
				
				artEditFormVisible: false,
				artEditForm: {},
				artEditFormRules: {
					title: [
						{ required: true, message: '请输入帖子标题', trigger: 'blur' }
					],
					content: [
						{ required: true, message: '请输入帖子内容', trigger: 'blur' }
					]},
				artEditLoading: false,
				
				editorOption: {
					modules: {
						toolbar: {
							container: [
					              /*['bold', 'italic', 'underline', 'strike'],
					              ['blockquote', 'code-block'],
					              [{ 'header': 1 }, { 'header': 2 }],
					              [{ 'list': 'ordered'}, { 'list': 'bullet' }],
					              [{ 'script': 'sub'}, { 'script': 'super' }],
					              [{ 'indent': '-1'}, { 'indent': '+1' }],
					              [{ 'direction': 'rtl' }],
					              [{ 'size': ['small', false, 'large', 'huge'] }],
					              [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
					              [{ 'color': [] }, { 'background': [] }],
					              [{ 'font': [] }],
					              [{ 'align': [] }],
					              ['clean'],
					              ['link', 'image']*/
								  ['bold', 'image']
					            ],
					            handlers: {
								    // handlers object will be merged with default handlers object
								    'image': function(value) {
								    	if(myvue.articleFormVisible){ // 发帖
								    		if(myvue.articleForm.style ==2){
									    		alert("同人图风格，帖子内容不可上传图片！");
									    	}else{
									    		myvue.handleUpload('editorFile');
									    	}
								    	}else if(myvue.artEditFormVisible){ // 编辑
								    		if(myvue.artEditForm.style ==2){
									    		alert("同人图风格，帖子内容不可上传图片！");
									    	}else{
									    		myvue.handleUpload('editFile2');
									    	}
								    	}
								    	
								    }

							  }
						}
					}
			          
			    },
			    editorImage: ''
				
			}
		},
		methods: {
			formatDate: function(date){
				return parent.window.formatDate(date, 'yyyy-MM-dd HH:mm:ss');
			},
			handleUpload: function(id, content){
				$('#'+id).attr("action", "../manage/file/upload.json");
				$('#'+id+' input[type=file]').click();
			},
			uploadData: function(id, content){
				var self = this;
				$('#'+id).ajaxSubmit({
			   		beforeSend: function() {
			   	    },
			   	    uploadProgress: function(event, position, total, percentComplete) {
			   	    },
			   	    success: function() {
			   	    },
			   		complete: function(xhr) {
			   			eval('self.'+content+ '= "' + JSON.parse(xhr.responseText).data + '";');
			   		}
			   	}); 
			},
			uploadData2: function(id, content){
				var self = this;
				$('#'+id).ajaxSubmit({
			   		beforeSend: function() {
			   	    },
			   	    uploadProgress: function(event, position, total, percentComplete) {
			   	    },
			   	    success: function() {
			   	    },
			   		complete: function(xhr) {
			   			var dataUrl = JSON.parse(xhr.responseText).data;
			   			var  index = (self.editor.getSelection() || {}).index || self.editor.getLength();
			   			self.editor.insertEmbed(index, 'image', dataUrl, 'user');
//			   			$('#'+id+' input[type=file]').val('');
			   		}
			   	}); 
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
				
				var url = baseUrl+"manage/article/find_paged_infos.json";
				var condition = {"isPublish":-1, "isCheck": -1}; 
				for ( var key in this.filters) {
					if(key == "type" && this.filters[key] && this.filters[key] != "0"){
						if(this.filters[key] != "1"){
							condition["roleId"] = this.filters[key];
						}else{
							condition[key] = this.filters[key];
						}
					}else if(key == "start" && this.filters[key]){
						condition["timeCreate>="] = new Date(String(this.filters[key]).replace("-","/")).getTime()/1000;
					}else if(key == "end" && this.filters[key]){
						condition["timeCreate<="] = new Date(String(this.filters[key]).replace("-","/")).getTime()/1000;
					}else if(this.filters[key]){
						condition[key] = this.filters[key];
					}
				}
				ajaxReq(url, {rows : params.rows, pageIndex: params.page,token: token, uid: uid, sortKeys: JSON.stringify({"timePublish":-1}), conditions: JSON.stringify(condition) }, function(res){
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
				this.$confirm('确认删除该记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/article/update.json";
					ajaxReq(url, {token: token, uid: uid, pid: row.pid, isPublish: 2 }, function(res){
						self.listLoading = false;
						if(res.code > 0){
							self.$message({
								message: '删除成功',
								type: 'success'
							});
							self.getList();
							//日志
							ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: row.pid}, function(res2){
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
										postId: row.pid,
										postType: row.style,
										gameType: row.appId,
										characterType: str ? str.substring(1) : "",
										amount: 0,
										ex1: '',
										ex2: '',
										ex3: '',
										ex4: 'del'
								};
								ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
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
			//删除
			handleRealDel: function (index, row) {
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
							ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: row.pid}, function(res2){
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
										postId: row.pid,
										postType: row.style,
										gameType: row.appId,
										characterType: str ? str.substring(1) : "",
										amount: 0,
										ex1: '',
										ex2: '',
										ex3: '',
										ex4: 'real'
								};
								ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
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
			//显示查看界面
			handleView: function (index, row) {
				this.viewFormVisible = true;
				this.viewForm = Object.assign({}, row);
			},
			//显示查看界面
			handleAuth: function (index, row) {
				this.authFormVisible = true;
				this.viewForm = Object.assign({}, row);
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
				this.editCacheForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
//				this.addFormVisible = true;
//				this.addForm = {
//					name: '',
//					appId: '',
//					status: true
//				};
			},
			//操作
			authSubmit: function (status) {
				this.$confirm('确认提交吗？', '提示', {}).then(() => {
					var self = this;
					this.authLoading = true;
					var url = baseUrl+"manage/article/update.json";
					ajaxReq(url, {token: token, uid: uid, pid: this.viewForm.pid, isCheck: status }, function(res){
						self.authLoading = false;
						if(res.code > 0){
							self.$message({
								message: '提交成功',
								type: 'success'
							});
							self.authFormVisible = false;
							self.getList();
							//日志
							ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: self.viewForm.pid}, function(res2){
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
										operateType: 12,//12:审核
										userType: 2,
										postId: self.viewForm.pid,
										postType: self.viewForm.style,
										gameType: self.viewForm.appId,
										characterType: str ? str.substring(1) : "",
										amount: 0,
										ex1: '',
										ex2: '',
										ex3: '',
										ex4: ''
								};
								ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
							});
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});
				});
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.editLoading = true;
							var url = baseUrl+"manage/article/update.json";
							var params = {
									token: token,
									uid: uid, 
									pid: this.editForm.pid, 
									isComment: this.editForm.isComment, 
									isTop: this.editForm.isTop, 
									isLight: this.editForm.isLight, 
									isRecommend: this.editForm.isRecommend  
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
									
									for ( var key in params) {
										if(key == "token") continue;
										if(key == "uid") continue;
										if(key == "pid") continue;
										for ( var key2 in self.editCacheForm) {
											if(key == key2){
												if(params[key] != self.editCacheForm[key2]){
													var operateType = (key == "isComment" ? 11 : key == "isTop" ? 9 : key == "isLight" ? 8 : 10); //8:加精 9:置顶10:推荐 11:可评论
													console.log(key+"-==-");
													console.log(operateType);
													(function(ot){
														//日志
														ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: self.editForm.pid}, function(res2){
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
																	operateType: ot,
																	userType: 2,
																	postId: self.editForm.pid,
																	postType: self.editForm.style,
																	gameType: self.editForm.appId,
																	characterType: str ? str.substring(1) : "",
																			amount: 0,
																			ex1: '',
																			ex2: '',
																			ex3: '',
																			ex4: ''
															};
															ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
														});
													})(operateType);
												}
											}
										}
									}
									
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
			//置顶
			handleTop: function (index, row) {
				var self = this;
				var url = baseUrl+"manage/article_role/find_by_keys.json";
				var params = {};
				params.token = token;
				params.uid = uid;
				params.artId = row.pid;
				ajaxReq(url, params, function(res){
					if(res.code > 0){
						self.topForm = {
								artId: row.pid,
								type: 0,
								sortId: 0
							};
						self.indeterminate = false;
						self.checkAll = false;
						self.checkedItems = [];
						self.items = [];  //初始化总数据
						for (var i = 0; i < res.data.length; i++) {
							self.items.push({
								roleId: res.data[i].roleId,
								name: res.data[i].name,
								sortId: 0
							});
						}
						var url = baseUrl+"manage/role_top/find_by_keys.json";
						var params = {};
						params.token = token;
						params.uid = uid;
						params.artId = row.pid;
						ajaxReq(url, params, function(res){
							if(res.code > 0){
								//初始化选中
								if(res.data.type == 0){
									self.topForm.type = Number(res.data.type);
									self.topForm.sortId = Number(res.data.sortId);
								}else if(res.data.type == 1){
									self.topForm.type = Number(res.data.type);
									self.checkedItems = [];
									for (var i = 0; i < res.data.roleList.length; i++) {
										var tmp = res.data.roleList[i];
										if(tmp.sortId){
											self.checkedItems.push(tmp.roleId);
											for (var j = 0; j < self.items.length; j++) {
												if(self.items[j].roleId == tmp.roleId){
													self.items[j].sortId = tmp.roleId+"_"+tmp.sortId;
													break;
												}
											}
										}
									}
								}
								//初始化全选
								if(self.checkedItems.length > 0 && self.checkedItems.length < self.items.length){
									self.indeterminate = true;
								}else{
									self.indeterminate = false;
								}
								if(self.checkedItems.length == self.items.length){
									self.checkAll = true;
								}else{
									self.checkAll = false;
								}
								self.topFormVisible = true;
							}else{
								self.$message({
									message: res.msg,
									type: 'warning'
								})
							}
						});
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
					
				});
			},
			handleCheckAllChange :function(){
				if(this.checkAll){
					this.indeterminate = false;
					this.checkedItems = [];
					for (var i = 0; i < this.items.length; i++) {
						this.checkedItems.push(this.items[i].roleId);
					}
					for (var i = 0; i < this.items.length; i++) {
						this.items[i].sortId = this.items[i].roleId+"_1";
					}
				}else{
					this.checkedItems = [];
					for (var i = 0; i < this.items.length; i++) {
						this.items[i].sortId = 0;
					}
				}
			},
			handleCheckItemChange :function(event){
				//子级效果
				var tmp = {};
				for (var i = 0; i < this.items.length; i++) {
					if(this.items[i].roleId == event.target.value){
						tmp = this.items[i];
						break;
					}
				}
				if(event.target.checked){
					tmp.sortId = tmp.roleId+"_1";
				}else{
					tmp.sortId = 0;
				}
				//父级效果
				if(this.checkedItems.length > 0 && this.checkedItems.length < this.items.length){
					this.indeterminate = true;
				}else{
					this.indeterminate = false;
				}
				if(this.checkedItems.length == this.items.length){
					this.checkAll = true;
				}else{
					this.checkAll = false;
				}
			},
			handleCheckItemChildChange :function(v){
				var strs = String(v).split('_');
				if(strs.length < 2) return;
				for (var i = 0; i < this.items.length; i++) {
					if(this.items[i].roleId == strs[0]){
						var canPush = true;
						for (var j = 0; j < this.checkedItems.length; j++) {
							if(this.checkedItems[j] == strs[0]){
								canPush = false;
								break;
							}
						}
						if(canPush){
							this.checkedItems.push(strs[0]);
						}
						break;
					}
				}
				if(this.checkedItems.length > 0 && this.checkedItems.length < this.items.length){
					this.indeterminate = true;
				}else{
					this.indeterminate = false;
				}
				if(this.checkedItems.length == this.items.length){
					this.checkAll = true;
				}else{
					this.checkAll = false;
				}
			},
			topSubmit: function (index, row) {
				this.$confirm('确认提交吗？', '提示', {}).then(() => {
					var self = this;
					this.topLoading = true;
					var url = baseUrl+"manage/role_top/save.json";
					var params = {};
					params.token = token;
					params.uid = uid;
					params.artId = this.topForm.artId;
					params.type = this.topForm.type
					
					if(params.type == 0){
						params.sortId = this.topForm.sortId;
					}else{
						params.roleList = [];
						for (var i = 0; i < this.items.length; i++) {
							var temp = {};
							if(this.items[i].sortId != 0){
								temp = this.items[i];
								temp.sortId = Number(this.items[i].sortId.split("_")[1]);
							}else{
								temp = this.items[i];
							}
							params.roleList.push(temp);
						}
					}
					params.roleList = JSON.stringify(params.roleList);
					ajaxReq(url, params, function(res){
						self.topLoading = false;
						if(res.code > 0){
							self.$message({
								message: '提交成功',
								type: 'success'
							});
							self.topFormVisible = false;
							self.getList();
							//日志
							ajaxReq(baseUrl+"manage/article/find_by_keys.json", {token: token, uid: uid, pid: self.topForm.artId}, function(res3){
								var artData = res3.data[0];
								if(!artData){return;}
								ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: self.topForm.artId}, function(res2){
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
											operateType: 9,//9:置顶
											userType: 2,
											postId: artData.pid,
											postType: artData.style,
											gameType: artData.appId,
											characterType: str ? str.substring(1) : "",
											amount: 0,
											ex1: '',
											ex2: '',
											ex3: '',
											ex4: ''
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
					
				});
			},
			//导出
			handleExcel: function (index, row) {
				this.$confirm('确认导出该记录的相关评论吗？', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					var url = baseUrl+"manage/comment/export_data.json";
					/*ajaxReq(url, {token: token, uid: uid, artId: row.pid }, function(res){
						if(res.code > 0){
							self.$message({
								message: '导出成功',
								type: 'success'
							});
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});*/
					var downExcel = baseUrl+"manage/comment/export_data.json?token="+token+"&uid="+uid+"&artId="+row.pid;
					$('#downExcel').attr("src",downExcel);
					//日志
					ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: row.pid}, function(res2){
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
								operateType: 16,//16:导出
								userType: 2,
								postId: row.pid,
								postType: row.style,
								gameType: row.appId,
								characterType: str ? str.substring(1) : "",
								amount: 0,
								ex1: '',
								ex2: '',
								ex3: '',
								ex4: ''
						};
						ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
					});
					
				}).catch(() => {
				});
			},
			//发帖
			handleArticle: function(){
				this.articleFormVisible = true;
				this.articleForm = {
					token: token,     
					uid: uid,          
					pid: '', 
					title: '',     
					color: '',     
					languageId: '',     
					appId: '',     
					roleIdList: [],     
					type: 0,     
					style: 0,     
					areaId: '',     
					sectionId: '',     
					content: '',  
					authorId: '4465005761812709',
					isPublish: 1,     
					isVisible: 2,  
					isComment: 1,     
					album: '',     
					url: ''
				};
				var self = this;
				var url2 = baseUrl+"manage/app/find_by_keys.json";
				var params2 = {};
				params2.token = token;
				params2.uid = uid;
				params2.isDelete = 0;
				ajaxReq(url2, params2, function(res){
						if(res.code > 0){
						   self.appsData = res.data;  //初始化总数据
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
				});
				var url3 = baseUrl+"manage/role/find_by_keys.json";
				var params3 = {};
				params3.token = token;
				params3.uid = uid;
				params3.isDelete = 0;
				ajaxReq(url3, params3, function(res){
						if(res.code > 0){
						   self.rolesData = res.data;  //初始化总数据
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
				});
			},
			addArticle: function(){
				this.$refs.articleForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.articleLoading = true;
							var url = baseUrl+"manage/article/add.json";
							var params = Object.assign({}, this.articleForm);
							params.roleIdList = JSON.stringify(params.roleIdList);
							ajaxReq(url, params, function(res){
								self.articleLoading = false;
								if(res.code > 0){
									self.$message({
										message: '提交成功',
										type: 'success'
									});
									self.articleFormVisible = false;
									self.getList();
									//日志
									ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: res.data.pid}, function(res2){
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
												operateType: 1,//1:发帖
												userType: 2,
												postId: res.data.pid,
												postType: self.articleForm.style,
												gameType: self.articleForm.appId,
												characterType: str ? str.substring(1) : "",
												amount: 0,
												ex1: '',
												ex2: '',
												ex3: '',
												ex4: ''
										};
										ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
									});
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
			handleArtEdit: function(index, row){
				this.artEditFormVisible = true;
//				this.artEditForm = Object.assign({}, row);
				var self = this;
				var url2 = baseUrl+"manage/app/find_by_keys.json";
				var params2 = {};
				params2.token = token;
				params2.uid = uid;
				params2.isDelete = 0;
				ajaxReq(url2, params2, function(res){
						if(res.code > 0){
						   self.appsData = res.data;  //初始化总数据
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
				});
				var url3 = baseUrl+"manage/role/find_by_keys.json";
				var params3 = {};
				params3.token = token;
				params3.uid = uid;
				params3.isDelete = 0;
				ajaxReq(url3, params3, function(res){
						if(res.code > 0){
						   self.rolesData = res.data;  //初始化总数据
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
				});
				this.artEditForm = {
						token: token,     
						uid: uid,          
						pid: row.pid, 
						title: row.title,     
						color: row.color,     
						languageId: row.languageId,     
						appId: row.appId,     
						roleIdList: row.roleIdList,     
						type: row.type,     
						style: row.style,     
						areaId: row.areaId,     
						sectionId: row.sectionId,     
						content: row.content,  
						authorId: row.authorId,
						isPublish: row.isPublish,     
						isVisible: row.isVisible,  
						isComment: row.isComment,     
						album: row.album,     
						url: row.url
					};
			},
			editArticle: function(){
				this.$refs.artEditForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.artEditLoading = true;
							var url = baseUrl+"manage/article/update.json";
							var params = Object.assign({}, this.artEditForm);
							params.roleIdList = JSON.stringify(params.roleIdList);
							params.token = token;     
							params.uid = uid;
							ajaxReq(url, params, function(res){
								self.artEditLoading = false;
								if(res.code > 0){
									self.$message({
										message: '提交成功',
										type: 'success'
									});
									self.artEditFormVisible = false;
									self.getList();
									//日志
									ajaxReq(baseUrl+"manage/article_role/find_by_keys.json", {token: token, uid: uid, artId: self.artEditForm.pid}, function(res2){
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
												operateType: 15,//15:更新
												userType: 2,
												postId: self.artEditForm.pid,
												postType: self.artEditForm.style,
												gameType: self.artEditForm.appId,
												characterType: str ? str.substring(1) : "",
												amount: 0,
												ex1: '',
												ex2: '',
												ex3: '',
												ex4: ''
										};
										ajaxReq(baseUrl+"manage/log/action.json", logParams, function(res){});
									});
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
			},
			  onEditorBlur(editor) {
//		        console.log('editor blur!', editor)
		      },
		      onEditorFocus(editor) {
//		        console.log('editor focus!', editor)
		      },
		      onEditorReady(editor) {
//		        console.log('editor ready!', editor)
		      },
		      onEditorChange({ editor, html, text }) {
//		        console.log('editor change!', editor, html, text)
		        this.content = html;
		      }
		},
		computed: {
	      editor: function() {
	        return this.$refs.myQuillEditor.quill;
	      }
	    },
		mounted: function() {
			this.getList();
			
			var self = this;
			var url2 = baseUrl+"manage/app/find_by_keys.json";
			var params2 = {};
			params2.token = token;
			params2.uid = uid;
			params2.isDelete = 0;
			ajaxReq(url2, params2, function(res){
					if(res.code > 0){
					   self.appsData = res.data;  //初始化总数据
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
			});
			var url3 = baseUrl+"manage/role/find_by_keys.json";
			var params3 = {};
			params3.token = token;
			params3.uid = uid;
			params3.isDelete = 0;
			ajaxReq(url3, params3, function(res){
					if(res.code > 0){
					   self.rolesData = res.data;  //初始化总数据
					}else{
						self.$message({
							message: res.msg,
							type: 'warning'
						})
					}
			});
		}
	  });
	
	
