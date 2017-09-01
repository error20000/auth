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
				//新增界面数据
				addFormVisible: false,//新增界面是否显示
				addLoading: false, //loading
				//数据
				addForm: {},
				//效验
				addFormRules: {
					artIds: [
						{ required: true, message: '请输入帖子ID', trigger: 'blur' }
					],
					giftId: [
						{ required: true, message: '请输入礼包ID', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editFormVisible: false,//编辑界面是否显示
				editLoading: false, //loading
				//数据
				editForm: {},
				//效验
				editFormRules: {
					artIds: [
						{ required: true, message: '请输入帖子ID', trigger: 'blur' }
					],
					giftId: [
						{ required: true, message: '请输入礼包ID', trigger: 'blur' }
					]
				},
				//查看界面数据
				viewFormVisible: false,//编辑界面是否显示
				viewLoading: false, //loading
				//数据
				viewForm: {},
				//审核界面数据
				authFormVisible: false,//编辑界面是否显示
				authLoading: false //loading
				
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
			//查询
			getList: function () {
				var self = this;
				var params = {
					page: this.page,
					rows: this.rows
				};
				this.listLoading = true;
				
				var url = baseUrl+"manage/comment_reward/find_paged_cfg.json";
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
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
						artIds: '',
						giftId: ''
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
				this.$confirm('确认删除该记录吗？删除后不可恢复。', '提示', {
					type: 'warning'
				}).then(() => {
					var self = this;
					this.listLoading = true;
					var url = baseUrl+"manage/comment_reward/delete_cfg.json";
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
//							params.artIds = JSON.stringify(this.addForm.artIds.split(","));
							var self = this;
							this.addLoading = true;
							var url = baseUrl+"manage/comment_reward/add_cfg.json";
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
							var url = baseUrl+"manage/comment_reward/update_cfg.json";
							var params = Object.assign({}, this.editForm);
							params.token = token;
							params.uid = uid;
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
	
