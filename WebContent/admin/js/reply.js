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
				checkAll: false
				
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
				
				var url = baseUrl+"manage/reply/find_paged_infos.json";
				ajaxReq(url, {rows : params.rows, pageIndex: params.page,token: token, uid: uid, conditions: JSON.stringify({"isCheck": -1}), sortKeys: JSON.stringify({"timeCreate":-1}) }, function(res){
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
					var url = baseUrl+"manage/reply/update.json";
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
					var url = baseUrl+"manage/reply/delete.json";
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
			//显示查看界面
			handleAuth: function (index, row) {
				this.authFormVisible = true;
				this.viewForm = Object.assign({}, row);
			},
			//操作
			authSubmit: function (status) {
				this.$confirm('确认提交吗？', '提示', {}).then(() => {
					var self = this;
					this.authLoading = true;
					var url = baseUrl+"manage/reply/update.json";
					ajaxReq(url, {token: token, uid: uid, pid: this.viewForm.pid, isCheck: status }, function(res){
						self.authLoading = false;
						if(res.code > 0){
							self.$message({
								message: '提交成功',
								type: 'success'
							});
							self.authFormVisible = false;
							self.getList();
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
	
	
