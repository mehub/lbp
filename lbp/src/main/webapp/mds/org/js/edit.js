function saveOrUpdate(e){
	$("#orgName").blur();
	$("#code").blur();
    if (e && e.preventDefault)
        e.preventDefault();
    if(!$.formValidator.pageIsValid(1)){
        return false;
    }
    ajaxAnywhere.formName="orgForm";
    ajaxAnywhere.getZonesToReload = function(){
        return "orgListTable";
    };
    ajaxAnywhere.submitAJAX();
    tb_remove();
    return true;
}

function selectRegion(id){
    var win = new Asc.windows.base({
        title:'区域',
        iframe:path+"/mds/region/jstree.jsp",
        width:300,
        height:300,
        buttons:[{
            text:'选择',
            cls:'bt2',
            fn:function(){
                var i = this.container.find('iframe:first')[0];
                var w = i.contentWindow?i.contentWindow:i;
                if(w.parentId.length>0){
                    $("#regionId").val(w.parentId);
                    $("#regionName").val(w.parentName);
                    this.closeWindow();
                }else{
                    Asc.windows.alert('提示',"请选择区域!");
                }
            }
        },{
            text:'取消',
            cls:'bt2',
            fn:function(){
                this.closeWindow();
            }
        }]
    });
}

$(function(){
    $.formValidator.initConfig({
        validatorgroup: "1",
        formid:"orgForm",
        onerror:function(msg){
            return false;
        },
        onsuccess:function(){
            return true;
        }
    });
    $("#orgName").formValidator({
        validatorgroup: "1",
        onshow:"请输入企业名称",
        onfocus:"企业名称不能为空"
    }).regexValidator({
        datatype:"enum",
        regexp:'notempty',
        onerror:"企业名称不能为空"
    }).ajaxValidator({
        type:'post',
        async : false,
        data:{oldName:""},
        url:'checkNameOrg.action?id='+$("#id").val(),
        datatype:'json',
        success:function(json){
            return json.data;
        },
        cache:false,
        error:function(){
            showTip("服务器可能 正繁忙，请稍候重试");
        },
        onerror: "企业名称已经存在！"
    });
    $("#code").formValidator({
        validatorgroup: "1",
        onshow:"请输入企业编码",
        onfocus:"企业编码不能为空"
    }).regexValidator({
        datatype:"enum",
        regexp:'notempty',
        onerror:"企业编码不能为空"
    }).ajaxValidator({
        type:'post',
        async : false,
        data:{oldCode:""},
        url:'checkCodeOrg.action?id='+$("#id").val(),
        datatype:'json',
        success:function(json){
            return json.data;
        },
        cache:false,
        error:function(){
            showTip("服务器可能 正繁忙，请稍候重试");
        },
        onerror: "企业编码已经存在！"
    });
    
//    $("#typeId").formValidator({
//        validatorgroup: "1",
//        onshow:"请选择企业类型",
//        onfocus:"选择企业不能为空"
//    }).regexValidator({
//        datatype:"enum",
//        regexp:'notempty',
//        onerror:"选择企业不能为空"
//    });
    $("#regionName").formValidator({
        validatorgroup: "1",
        onshow:"请选择区域",
        onfocus:"区域不能为空"
    }).regexValidator({
        datatype:"enum",
        regexp:'notempty',
        onerror:"区域不能为空"
    });
    
    $("#telephone").formValidator({
        validatorgroup: "1",
        onshow:"电话号码5-18位",
        empty:true
    }).inputValidator({min:5,max:18}).regexValidator({regexp:'^(\\d{2,4}-?)?\\d{5,10}(-\\d{2,4})?$'});
    $("#email").formValidator({
        validatorgroup: "1",
        empty:true
    }).regexValidator({datatype:"enum", regexp:'email'});
});
