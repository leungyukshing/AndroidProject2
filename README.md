# 项目2

# 中山大学智慧健康服务平台应用开发

------

------

## Week7  

## Broadcast 使用

------

### 实验内容

在第六周任务的基础上，实现静态广播、动态广播两种改变Notification 内容的方法。  

#### 要求  

- 在启动应用时，会有通知产生，随机推荐一个食品。  
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week7_static_notification.jpg)
- 点击通知跳转到所推荐食品的详情界面。  
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week7_static_jump.jpg) 
- 点击收藏图标，会有对应通知产生，并通过Eventbus在收藏列表更新数据。  
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week7_requirement3.jpg) 
- 点击通知返回收藏列表。  
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week7_requirement4.jpg) 
- 实现方式要求:启动页面的通知由静态广播产生，点击收藏图标的通知由动态广播产生。   

------

------

## Week8

## AppWidget 使用

------

### 实验内容

在第七周任务的基础上，实现静态广播、动态广播两种改变widget内容的方法。  

#### 要求 

- widget初始情况如下：      
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week8_begin.PNG) 
- 点击widget可以启动应用，并在widget随机推荐一个食品。      
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week8_recommendation.PNG)
- 点击widget跳转到所推荐食品的详情界面。     
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week8_jump.PNG) 
- 点击收藏图标，widget相应更新。     
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week8_update.PNG) 
- 点击widget跳转到收藏列表。     
  ![preview](https://raw.githubusercontent.com/leungyukshing/AndroidProjectPhoto/master/lab2/week8_collection.PNG) 
- 实现方式要求:启动时的widget更新通过静态广播实现，点击收藏图标时的widget更新通过动态广播实现。 

