
function ajaxForm(formId, opt) {
    var form = $('#' + formId);
    if (opt === null) {
        opt = function (data) {
        }
    }
    $.ajax({
        url: form.attr('action'),
        type: form.attr('method'),
        data: form.serialize(),
        dataType: 'json',
        success: opt
    })
}

function showQuestion() {
    $.get('/show_question'+'?account='+$("#account").val(),function (data) {
        $('#question').text(data);
    });
}

function certify(id) {
    $.get('/certify',
        {"account":id},
        function (data) {
            if(data=='1'){
                $('#'+id).attr('disabled',true);
                alert("认证成功！");
            }else{
                alert("认证失败！");
            }
        })
}

function deleteUser(id) {
    $.get('/delete_user',
        {"account":id},
        function (data) {
            if(data=='1'){
                $('#'+id).attr('disabled',true);
                alert("注销成功！");
            }
            else {
                alert("注销失败！");
            }
        })
}
function showUserNoCertify() {
    $.get('/show_user_no_certify',
        function (data) {
            list=eval(data);
            h="<tr><th class='td' style='width: 75%'>用户名</th>" +
                "<th style='width: 25%'>通过</th></tr>";
            for(var i=0;i<list.length;++i){
                h+="<tr><td class='td'>"+list[i].account+"</td>" +
                    "<td><button id='"+list[i].account+"' onclick=certify('"+list[i].account+"')" +
                    " style="+
                    "'width:100%'"+
                    ">确定</td></tr>";
            }
            $('#container_left').empty();
            $('#container_left').html(h);
        },'JSON')
}
function showUsers() {
    $.get('/get_users',
        function (data) {
            list=eval(data);
            h="<tr><th class=\"td\" style=\"width: 25%;\">用户名</th>" +
                "<th class=\"td\" style=\"width: 25%;\">姓名</th>" +
                "<th class=\"td\" style=\"width: 25%;\">电话号码</th>" +
                "<th class=\"td\" style=\"width: 5%\">年龄</th>" +
                "<th class=\"td\" style=\"width: 5%\">性别</th>" +
                "<th class=\"td\" style=\"width: 5%;\">类型</th>" +
                "<th class=\"td\" style=\"width: 10%;\">注销</th>" +
                "</tr>";
            for(var a=0;a<list.length;++a){
                i=list[a];
                h+="<tr><td class=\"td\" style=\"width: 25%;\">"+i.account+"</td>"+
                    "<td class=\"td\" style=\"width: 25%;\">"+i.name+"</td>" +
                    "<td class=\"td\" style=\"width: 25%;\">"+i.telephone+"</td>"+
                    "<td class=\"td\" style=\"width: 5%\">"+i.age+"</td>" +
                    "<td class=\"td\" style=\"width: 5%\">"+i.sex+"</td>" +
                    "<td class=\"td\" style=\"width: 5%;\">"+i.type+"</td>" +
                    "<td><button class='td' style='width: 100%' id='"+
                    i.account+"'"+
                    "onclick='deleteUser("+i.account+")'"+
                    ">确定注销</button></td></tr>"
            }
            $('#container_right').empty();
            $('#container_right').html(h);
        },'JSON')
}

function getAllProducts() {
    $.get('/getProducts',function (data,status) {
        var total=0;
        var totalNum=0;
        if(status==='error')
            return;
        list=eval(data);
        h="<tr>\n" +
            "<th>商品ID</th>\n" +
            "<th>商品名称</th>\n" +
            "<th>商品价格</th>\n" +
            "<th>剩余数量</th>\n" +
            "<th>总价</th>\n" +
            "<th>进货</th>\n" +
            "</tr>";
        for(var i=0;i<list.length;++i){
            total+=list[i].price*list[i].number;
            totalNum+=list[i].number;
            h+="<tr>" +
                "<td align=\"center\">"+list[i].id+"</td>\n" +
                "<td align=\"center\">"+list[i].name+"</td>\n" +
                "<td align=\"center\">"+list[i].price+"</td>\n" +
                "<td align=\"center\" id='"+list[i].id+"num"+"'>"+list[i].number+"</td>\n" +
                "<td align=\"center\" id='"+list[i].id+"total"+"'>"+list[i].price*list[i].number+"</td>\n" +
                "<td align=\"center\"><button onclick=\"addProducts('"+list[i].id+"')\">进货</button></td>\n" +
                "</tr>"
        }
        $("#resultContanier").text("共计："+totalNum+"   总价："+total+"元   均价："+numeral(total/totalNum).format("0,0.00")+"元");
        $("#containerUp").empty();
        $("#containerUp").html(h);
    },'JSON');
}
function addProducts(id) {
    var num=parseInt(prompt("请输入进货数量","1"));
    $.post("/add_products",
        {
            id:id,
            number:num
        },function (data) {
            product=eval(data);
            $("#"+id+"num").text(product.number);
            $("#"+id+"total").text(product.price*product.number);
            alert("更新成功！");
        },"JSON");
}

function addNewProduct() {
    if (isNaN($("#price").val()) || isNaN($("#number").val())) {
        alert("请输入有效的价格和数量");
        return;
    }
    var price = parseFloat($("#price").val());
    var number = parseInt($("#number").val());
    console.log("price:"+price+"number:"+number);
    if (price >= 0 && number >= 0) {
        $.post("/add_new_product",
            {
                name: $("#name").val(),
                price: price,
                number: number
            }, function (data) {
                $("#name").text("");
                $("#price").text("");
                $("#number").text("");
                alert(data);
                window.location.reload();
            });
    }
    else{
        alert("请输入有效的价格和数量");
    }
}

function modifyInfo() {
    $.post("/modify_info",
        {
            name:$("#name").val(),
            telephone:$("#telephone").val(),
            age:parseInt($("#age").val()),
            sex:parseInt($("#sex").val()),
        },function (data) {
            if(data==false)
                alert("更新失败！");
            else
                alert("更新成功！");
        },"TEXT");
}
var cloneObj = function (obj) {
    var newObj = {};
    if (obj instanceof Array) {
        newObj = [];
    }
    for (var key in obj) {
        var val = obj[key];
        newObj[key] = typeof val === 'object' ? cloneObj(val): val;
    }
    return newObj;
};

//cashier
var productsMap;
var productsList;
var shoppingMap;
var totalMoney;
var totalNumber;
function loadList() {
    productsMap=new Array();
    shoppingMap=new Array();
    totalNumber=0;
    totalMoney=0;
    $.ajax({
        url:"/getProducts",
        type:"get",
        dataType:"JSON",
        async:false,
        success:function (data,status) {
            productsList=eval(data);
            for(var i=0;i<productsList.length;++i){
                productsMap[productsList[i].id]=productsList[i];
                shoppingMap[productsList[i].id]=cloneObj(productsList[i]);
                shoppingMap[productsList[i].id].number=0;
            }
    }});
}
function showProducts(){
    h="<tr>\n" +
        "<th>商品名称</th>\n" +
        "<th>商品价格</th>\n" +
        "<th>剩余数量</th>\n" +
        "<th>加入购物车</th>\n" +
        "</tr>";
    for(i in productsMap){
        var x=productsMap[i];
        if(x.number>0){
            h+="<tr>" +
                "<td align=\"center\">"+x.name+"</td>\n" +
                "<td align=\"center\" id='"+x.id+"price"+"'>"+x.price+"</td>\n" +
                "<td align=\"center\" id='"+x.id+"num"+"'>"+x.number+"</td>\n" +
                "<td align=\"center\"><button onclick=\"collect('"+x.id+"')\">添加</button></td>\n" +
                "</tr>";
        }
    }
    $("#containerUp").empty();
    $("#containerUp").html(h);
}

function showShoppingMap() {
    h="<tr>" +
        "<th>商品名</th>" +
        "<th>数量</th>" +
        "<th>价格</th>" +
        "<th>撤回</th>"+
        "</tr>";
    for(i in shoppingMap){
        var x=shoppingMap[i];
        if(x.number>0){
            h+="<tr><td align=\"center\">"+x.name+"</td>" +
                "<td align=\"center\">"+x.number+"</td>" +
                "<td align=\"center\">"+x.number*x.price+"</td>"+
                "<td align=\"center\"><button onclick=\"reSet('"+x.id+"')\">撤回</button></td></tr>";
        }
    }
    $("#shoppingContain").empty();
    $("#shoppingContain").html(h);
    $("#totalNumber").text("总量："+totalNumber);
    $("#totalMoney").text("总价："+totalMoney);
}
function collect(id) {
    var num=parseInt(prompt("请输入商品数量","1"));
    if(!isNaN(num)&&num>0){
        if(num>productsMap[id].number){
            alert("数量超出");
            return;
        }
        productsMap[id].number-=num;
        shoppingMap[id].number+=num;
        totalNumber+=num;
        totalMoney+=num*productsMap[id].price;
    }
    showProducts();
    showShoppingMap();
}

function reSet(id) {
    productsMap[id].number+=shoppingMap[id].number;
    totalNumber-=shoppingMap[id].number;
    totalMoney-=shoppingMap[id].number*shoppingMap[id].price;
    shoppingMap[id].number=0;
    showProducts();
    showShoppingMap();
}

function refresh() {
    loadList();
    showProducts();
    $("#shoppingContain").empty();
    $("#totalNumber").text("总量："+totalNumber);
    $("#totalMoney").text("总价："+totalMoney);
}
function buy() {
    var list=[];
    var idx=0;
    for(i in shoppingMap){
        if(shoppingMap[i].number>0){
            list[idx]=shoppingMap[i];
            idx++;
        }
    }
    if(idx===0){
        alert("购物车为空！");
        return;
    }
    $.ajax({
        url:"/buy",
        type:"POST",
        dataType:"JSON",
        data: {
            order:JSON.stringify(list)
        },
        success:function (data) {
            var res=[];
            var idx=0;
            var flag=true;
            console.log(data);
            var l=eval(data);
            for(i in l){
                if(l[i]==false){
                    flag=false;
                    res[idx]=i;
                }
            }
            refresh();
            if(flag)
                alert("购买成功！");
            else
                alert("部分物品购买出错！");
        }
        ,complete:function (data,status) {
            if(status!="success"){
                console.log(status);
            }
        }
    });
}

Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var orderList;
function loadAllOrder() {
    $.ajax({
        url:"/get_all_order",
        type:"get",
        dataType:"JSON",
        success:function (data) {
            orderList=eval(data);
            showAllOrder();
        }
    });
}

function showAllOrder() {
    var h="<tr>\n" +
        "        <th>产品名称</th>\n" +
        "        <th>订单数量</th>\n" +
        "        <th>订单单价</th>\n" +
        "        <th>订单总价</th>\n" +
        "        <th>收银员</th>\n" +
        "        <th>下单时间</th>\n" +
        "    </tr>";
    for(var i=0;i<orderList.length;++i){
        var x=orderList[i];
        var oTime= new Date(x.orderTime).Format("yyyy-MM-dd hh:mm:ss");
        h+="<tr>"+
            "<td align=\"center\">"+x.productName+"</td>"+
            "<td align=\"center\">"+x.number+"</td>"+
            "<td align=\"center\">"+x.unitPrice+"</td>"+
            "<td align=\"center\">"+x.number*x.unitPrice+"</td>"+
            "<td align=\"center\">"+x.cashierName+"</td>"+
            "<td align=\"center\">"+oTime+"</td></tr>";
    }
    $("#container").empty();
    $("#container").html(h);
}

function loadPerformance() {
    $.get("/get_performance",function (data) {
        console.log(data);
        var l=eval(data);
        var h="<tr>\n" +
            "        <th>收银员姓名</th>\n" +
            "        <th>销售商品数</th>\n" +
            "        <th>销售总价</th>\n" +
            "    </tr>";
        for(var i=0;i<l.length;++i){
            var x=l[i];
            h+="<tr><td align=\"center\">"+x.name+"</td>"+
                "<td align=\"center\">"+x.totalNumber+"</td>"+
                "<td align=\"center\">"+x.totalMoney+"</td></tr>";
        }
        $("#container").empty();
        $("#container").html(h);
    },"JSON");
}

function showMyOrder() {
    var h="<tr>\n" +
        "        <th>产品名称</th>\n" +
        "        <th>订单数量</th>\n" +
        "        <th>订单单价</th>\n" +
        "        <th>订单总价</th>\n" +
        "        <th>下单时间</th>\n" +
        "    </tr>";
    for(var i=0;i<orderList.length;++i){
        var x=orderList[i];
        var oTime= new Date(x.orderTime).Format("yyyy-MM-dd hh:mm:ss");
        h+="<tr>"+
            "<td align=\"center\">"+x.productName+"</td>"+
            "<td align=\"center\">"+x.number+"</td>"+
            "<td align=\"center\">"+x.unitPrice+"</td>"+
            "<td align=\"center\">"+x.number*x.unitPrice+"</td>"+
            "<td align=\"center\">"+oTime+"</td></tr>";
    }
    $("#container").empty();
    $("#container").html(h);
}

function loadMyOrder() {
    $.ajax({
        url:"/get_my_order",
        type:"get",
        dataType:"JSON",
        success:function (data) {
            orderList=eval(data);
            showMyOrder();
        }
    });
}
var performanceList;
function showPerformance() {
    var totalNumber=0;
    var totalMoney=0;
    var h="<tr>\n" +
        "        <th>产品名称</th>\n" +
        "        <th>售出数量</th>\n" +
        "        <th>商品单价</th>\n" +
        "        <th>该物品总价</th>\n" +
        "    </tr>";
    for(var i=0;i<performanceList.length;++i){
        var x=performanceList[i];
        totalNumber+=x.number;
        totalMoney+=x.number*x.price;
        h+="<tr><td align=\"center\">"+x.name+"</td>"+
            "<td align=\"center\">"+x.number+"</td>"+
            "<td align=\"center\">"+x.price+"</td>"+
            "<td align=\"center\">"+x.number*x.price+"</td></tr>";
    }
    $("#container").empty();
    $("#container").html(h);
    $("#containerButtom").html("共售出："+totalNumber+"件商品<br>总价："+totalMoney+"元<br>均价："
        +numeral(totalMoney/totalNumber).format("0,0.00")+"元");
}
function loadMyPerformance() {
    $.get("/get_my_performance",function (data) {
        performanceList=eval(data);
        showPerformance();
    },"JSON")
}

function loadAllPerformance() {
    //showSelectForm();
    $.get("/get_sales_performance",function (data) {
        performanceList=eval(data);
        showPerformance();
    },"JSON")
}

function showSelectForm() {
    var h="<select style='float: left'>";
    for(var i=2010;i<=2018;++i){
        h+="<option value=\""+i+ "\">"+i+"年</option>";
    }
    h+="</select>";
    $("#selectMonthContainer").empty();
    $("#selectMonthContainer").html(h);
}

function checkPerformanceByMonth() {
    $.get("/get_sales_performance_by_month",{
        year:parseInt($("#year").val()),
        month:parseInt($("#month").val())
    },function (data) {
        performanceList=eval(data);
        showPerformance();
    },"JSON")
}