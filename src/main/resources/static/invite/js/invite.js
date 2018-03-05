//判断设备系统名称
function isSysName(){
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        return "iphone";
    } else if (/android/.test(ua)) {
        return "android";
    }
}
//获取url参数
function getQueryString(str){
 	var rs=new RegExp("(^|)"+str+"=([^&]*)(&|$)","gi").exec(String(window.document.location.href)),tmp;

 	console.log(rs);
 	if(tmp=rs)return tmp[2];
 	return null;
}
//提示黑底白字框
Vue.component('error-tip',{
	template:'\
		<div v-show="errorShowCom" :style="errorDivCss">\
			<div :style="errorTipCss">{{errorMessage}}</div>\
		</div>\
	',
	props:['errorMessage','errorShowCom'],
	data:function(){
		return{
			errorTipCss:{
				width:'80%',
				position:'absolute',
				left:'10%',
				top:'40%',
				backgroundColor:'#000000',
				color:'#FFFFFF',
				borderRadius:'10px',
				textAlign:'center',
				padding:'2%',
				boxSizing:'border-box'
			},
			errorDivCss:{
				width:'100%',
				height:'100%',
				position:'fixed',
				left:0,
				top:0
			}
		}
	},
	watch:{
		errorShowCom:function(newVal,oldVal){
			if(!oldVal&&newVal){
				var that=this;
				setTimeout(function(){
					that.$emit('disappear');
				},5000);
			}
		}
	}
});
//加载中组件
Vue.component('loading',{
	template:'<div :style="loadingDivCss"></div>',
	data:function(){
		return{
			loadingDivCss:{
				width:'100%',
				height:'100%',
				position:'fixed',
				left:0,
				top:0
			}
		}
	}
});

var invite=new Vue({
	el:'#invite',
	data:{
		page:true,
		agreementShow:false,
		inviter:'',
		inviteCode:'',
		checked:true,
		showError:false,
		errorMessage:'',
		phone:'',
		sent:false,
		second:'',
		verificationCode:'',
		loadingShow:false
	},
	methods:{
		getInviteCode:function(){
			var that=this;
			if(/^1[34578]\d{9}$/.test(this.phone)){
				axios({
					method:'GET',
					url:'/dafeng/getIdentifyWhenInvite',
					params:{
						phone:that.phone
					}
				}).then(function(response){
					if(response.data.ErrorCode==0){
						that.sent=true;
						that.second=60;
						var timer=setInterval(function(){
							that.second=that.second-1;
							if(that.second===0){
								clearInterval(timer);
								that.sent=false;
								that.second=60;
							}
						},1000);						
					}
					else{
						that.errorMessage=response.data.ErrorInfo;
						that.showError=true;
					}
				}).catch(function(response){
					that.errorMessage='发送失败';
					that.showError=true;
				});
			}
			else{
				this.errorMessage='手机号格式错误';
				this.showError=true;
			}
		},
		register:function(){
			if(!this.phone||!/^1[34578]\d{9}$/.test(this.phone)){
				this.errorMessage='请输入正确手机号';
				this.showError=true;
			}
			else if(!this.verificationCode){
				this.errorMessage='请输入验证码';
				this.showError=true;
			}
			else if(!this.checked){
				this.errorMessage='请同意用户协议';
				this.showError=true;
			}
			else{
				this.loadingShow=true;
				var that=this;
				axios({
					method:'POST',
					url:'/dafeng/loginIn',
					data:{
						'phone':that.phone,
						'code':that.verificationCode,
						'invite':that.inviteCode
					}
				}).then(function(response){
					that.loadingShow=false;
					if(response.data.ErrorCode==0){
						that.page=false;
					}
					else{
						that.errorMessage=response.data.ErrorInfo;
						that.showError=true;
					}
				}).catch(function(response){
					that.loadingShow=false;
					that.errorMessage='注册失败';
					that.showError=true;
				});
			}
		},
		downloadAPP:function(){
			if(isSysName()==='iphone'){
				window.location.href='https://itunes.apple.com/cn/app/%E5%A4%A7%E8%9C%82%E4%BF%9D%E9%99%A9/id1288386903?l=zh&ls=1&mt=8';
			}
			else if(isSysName()==='android'){
				window.location.href='https://www.pgyer.com/kn64';
			}
		}
	},
	created:function(){
		this.inviter=decodeURI(getQueryString('inviter'));
		this.inviteCode=getQueryString('inviteCode');
	}
});