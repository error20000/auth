var baseUrl = parent.window.baseUrl || '../';
var token = parent.window.token || "";
var uid = parent.window.uid || "";

var ajaxReq = parent.window.ajaxReq || "";


var myvue = new Vue({
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
				addFormRules: {
					name: [
						{ required: true, message: '请输入名称', trigger: 'blur' }
					],
					imageUrl: [
						{ required: true, message: '请上传图片', trigger: 'blur' }
					],
					description: [
						{ required: true, message: '请输入描述', trigger: 'blur' }
					],
					sortId: [
						{ required: true, message: '请输入排序序号', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editFormVisible: false,//编辑界面是否显示
				editLoading: false, //loading
				//数据
				editForm: {},
				//效验
				editFormRules: {
					imageUrl: [
						{ required: true, message: '请上传图片', trigger: 'blur' }
					],
					description: [
						{ required: true, message: '请输入描述', trigger: 'blur' }
					],
					sortId: [
						{ required: true, message: '请输入排序序号', trigger: 'blur' }
					]
				},
				//查看界面数据
				viewFormVisible: false,//编辑界面是否显示
				viewLoading: false, //loading
				//数据
				viewForm: {},
				//审核界面数据
				authFormVisible: false,//编辑界面是否显示
				authLoading: false,
				
				//标签界面数据
				labelFormVisible: false,//编辑界面是否显示
				labelLoading: false, //loading
				labelName: '', 
				//数据
				labelForm: {},
				//效验
				labelFormRules: {},
				indeterminate: false,
				items: [],
				checkedItems: {},
				checkAll: false
				
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
			handleSizeChange: function (val) {
				this.rows = val;
				this.getList();
			},
			handleCurrentChange: function (val) {
				this.page = val;
				this.getList();
			},
			//查询
			getList: function () {
				var self = this;
				var params = {
					page: this.page,
					rows: this.rows
				};
				this.listLoading = true;
				
				var url = baseUrl+"manage/role/find_paged_infos.json";
				ajaxReq(url, {rows : params.rows, pageIndex: params.page,token: token, uid: uid, sortKeys: JSON.stringify({"timeCreate":-1}) }, function(res){
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
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
						name: '',
						roleId: '',
						imageUrl: '',
						swiperNormal: '',
						swiperSelected: '',
						swiperBanner: '',
						description: '',
						status: 0,
						sortId: ''
				};
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
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/role/update.json";
					ajaxReq(url, {token: token, uid: uid, pid: row.pid, isDelete: 1 }, function(res){
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
			//彻底删除
			handleRealDel: function (index, row) {
				this.$confirm('确认删除该记录吗？删除后不可恢复。', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/role/delete.json";
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
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var params = Object.assign({}, this.addForm);
							params.token = token;
							params.uid = uid;
							var self = this;
							this.addLoading = true;
							var url = baseUrl+"manage/role/add.json";
							ajaxReq(url, params, function(res){
								self.addLoading = false;
								if(res.code > 0){
									self.$message({
										message: '新增成功',
										type: 'success'
									});
									self.addFormVisible = false;
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
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							var self = this;
							this.editLoading = true;
							var url = baseUrl+"manage/role/update.json";
							var params = Object.assign({}, this.editForm);
							params.token = token;
							params.uid = uid;
							delete params.name;
							delete params.roleId;
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
			//标签
			handleBind: function (index, row) {
				var self = this;

				var url = baseUrl+"manage/app/find_by_keys.json";
				var params = {};
				params.token = token;
				params.uid = uid;
				params.isDelete = 0;
				ajaxReq(url, params, function(res){
					if(res.code > 0){
						self.items = res.data;  //初始化总数据
						var url = baseUrl+"manage/role_bind/find_by_keys.json";
						var params = {};
						params.token = token;
						params.uid = uid;
						params.roleId = row.roleId;
						ajaxReq(url, params, function(res){
							if(res.code > 0 || res.code == -1000){
								//初始化选中
								self.checkedItems = [];
								for (var i = 0; i < res.data.length; i++) {
									self.checkedItems.push(res.data[i].appId);
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
								self.labelFormVisible = true;
								self.labelForm = row;
								self.labelName = row.name;
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
						this.checkedItems.push(this.items[i].appId)
					}
				}else{
					this.checkedItems = [];
				}
			},
			handleCheckItemChange :function(){
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
			labelSubmit: function (index, row) {
				this.$confirm('确认提交吗？', '提示', {}).then(() => {
					var self = this;
					this.labelLoading = true;
					var url = baseUrl+"manage/role_bind/add.json";
					var params = {};
					params.token = token;
					params.uid = uid;
					params.roleId = this.labelForm.roleId;
					params.appIdList = JSON.stringify(this.checkedItems);
					ajaxReq(url, params, function(res){
						self.labelLoading = false;
						if(res.code > 0){
							self.$message({
								message: '提交成功',
								type: 'success'
							});
							self.labelFormVisible = false;
						}else{
							self.$message({
								message: res.msg,
								type: 'warning'
							})
						}
					});
					
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
	
	

