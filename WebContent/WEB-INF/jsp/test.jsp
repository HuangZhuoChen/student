<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
layer.open({
      type : 1,
      maxmin : true,
      btnAlign : 'c',
      area : ['400px', '200px'],
      btn : ['按钮1', '按钮2', '按钮3'],
      yes : function(index, layero) {
            console.log("按钮一");
            //手动关闭
            layer.close(index);
      },
      btn2 : function(index, layero) {
            console.log("按钮二");
            return false; //开启该代码可禁止点击该按钮关闭
      },
      btn3 : function(index, layero) {
            console.log("按钮三");
      },
      cancel : function(index, layero) {//右上角关闭按钮触发的回调
            if(confirm('确定要关闭么')){ //只有当点击confirm框的确定时，该层才会关闭
                  layer.close(index)
            }
            return false;
      },
      end : function() {//无论是确认还是取消，只要层被销毁了，end都会执行，不携带任何参数
            console.log("end 销毁");
      },
      full : function(layero) {//最大化
            console.log("full");
      },
      min : function(layero) {//最小化
            console.log("min");
      },
      restore : function(layero) {//还原
            console.log("restore");
      },
});
</body>
</html>