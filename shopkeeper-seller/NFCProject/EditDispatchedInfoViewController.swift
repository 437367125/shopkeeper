//
//  ProfileViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/15/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit

var purchasestotalPrice = ["订单1","订单2","订单3"]
var purchasesbankcardId = ["订单1","订单2","订单3"]
var purchasesaddressId = ["订单1","订单2","订单3"]
var purchasesstatus = ["订单1","订单2","订单3"]
var purchasescreateTime = ["订单1","订单2","订单3"]
var purchasespayTime = ["订单1","订单2","订单3"]
var purchasesdeliveryTime = ["订单1","订单2","订单3"]
var purchasescompleteTime = ["订单1","订单2","订单3"]
var purchasescancelTime = ["订单1","订单2","订单3"]



class EditDispatchedInfoViewController: UIViewController, UITableViewDataSource, UITableViewDelegate
{
    var info = ["something"]

    @IBOutlet weak var btn: UIButton!
    @IBOutlet weak var titleLabel: UILabel!
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return info.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell1 = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell1")
        cell1.textLabel?.text = info[indexPath.row]
        return cell1
    }
    
    @IBAction func EditDispatchedState(_ sender: UIButton) {
      
       var res =  changeDispatched(token: MainToken, param: purchasesId[myIndex3])
        if res.contains("0000")
        {
            createAlert(title: "", message: "确认发货成功！")
        }
        else
        {
                createAlert(title: "", message: "确认发货失败！")
        }
        print("确认发货 tabbed")
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    

    
    override func viewDidLoad() {
      
        purchasestotalPrice = getOrderPrice(token: MainToken)
        purchasesbankcardId = getbankcardIdNumber(token: MainToken)
        purchasesaddressId = getOrderaddressId(token: MainToken)
        purchasesstatus = getOrderstatus(token: MainToken)
        purchasescreateTime = getOrdercreateTime(token: MainToken)
        purchasespayTime = getOrderpayTime(token: MainToken)
        purchasesdeliveryTime = getOrderdeliveryTime(token: MainToken)
        purchasescompleteTime = getOrdercompleteTime(token: MainToken)
        purchasescancelTime = getOrdercancelTime(token: MainToken)
        
        if purchasesstatus[myIndex3].contains("2")
        {
            btn.isEnabled = true
        }
       else
        {
            btn.isEnabled = false
        }
        
        
         info = getInformation()
        titleLabel.text = "订单信息"
    }
    
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    public func getInformation() -> [String]
    {
        var number = String()
        var totalPrice = String()
        var bankcardId = String()
        var addressId = String()
        var status = String()
        var createTime = String()
        var payTime = String()
        var deliveryTime = String()
        var completeTime = String()
        var cancelTime = String()
        
        
     
        
        
        number = purchasesNumber[myIndex3]
        totalPrice = purchasestotalPrice[myIndex3]
        bankcardId = purchasesbankcardId[myIndex3]
        addressId = purchasesaddressId[myIndex3]
        status = purchasesstatus[myIndex3]
        createTime = purchasescreateTime[myIndex3]
        payTime = purchasespayTime[myIndex3]
        deliveryTime = purchasesdeliveryTime[myIndex3]
        completeTime = purchasescompleteTime[myIndex3]
        cancelTime = purchasescancelTime[myIndex3]
  
        if(cancelTime.contains("null"))
        {
            cancelTime = "无"
        }
        if(addressId.contains("null"))
        {
            addressId = "无"
        }
         if(completeTime.contains("null"))
        {
            completeTime = "无"
        }
        if(deliveryTime.contains("null"))
        {
            completeTime = "无"
        }
      
        if(status == "0")
        {
            status = "待付款"
        }
        else if(status == "1")
        {
            status = "已付款"
        }
        else if(status == "2")
        {
            status = "待发货"
        }
        else if(status == "3")
        {
            status = "已发货"
        }
        else if(status == "4")
        {
            status = "已取消"
        }
        else if(status == "5")
        {
            status = "已完成"
        }
        return ["订单号： \(number)" , "Total Price：  \(totalPrice)","Address ：  \(addressId)","Status：  \(status)","Create Time \(createTime)","Pay Time \(payTime)","Delivery Time \(deliveryTime)","Complete Time \(completeTime)","Cancel Time \(cancelTime)"]
    }
    
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////// Order Oserration
    
    /////////////////////////////////////////////////////////////////////////////////get Price of all orders
    func getOrderPrice(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("totalPrice"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get bankcardId of all orders
    func getbankcardIdNumber(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("bankcardId"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get addressId of all orders
    func getOrderaddressId(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("addressId"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get status of all orders
    func getOrderstatus(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("status"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get createTime of all orders
    func getOrdercreateTime(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("createTime"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: ":")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get payTime of all orders
    func getOrderpayTime(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("payTime"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: ":")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get deliveryTime of all orders
    func getOrderdeliveryTime(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("deliveryTime"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: ":")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get completeTime of all orders
    func getOrdercompleteTime(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("completeTime"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: ":")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get cancelTime of all orders
    func getOrdercancelTime(token : String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("cancelTime"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: ":")
                var mainPhoneNumber = temp1.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                //print("\(k)     \(FinalProducts[k])")
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    func getOrderToken(token : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/userorder/all-list"
        let url = URL(string:urlString)
        //创建请求对象
        var request = URLRequest(url: url!)
        request.setValue(token, forHTTPHeaderField: "token")
        let session = URLSession.shared
        var res = ""
        let dataTask = session.dataTask(with: request,
                                        completionHandler: {(data, response, error) -> Void in
                                            if error != nil{
                                                print(error.debugDescription)
                                            }else{
                                                let str = String(data: data!, encoding: String.Encoding.utf8)
                                                res = str!
                                                
                                            }
        }) as URLSessionTask
        
        //使用resume方法启动任务
        dataTask.resume()
        while res == "" {
            
        }
        return res
        
    }
    func getOrderCom(token : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/userorder/all-list"
        let url = URL(string:urlString)
        //创建请求对象
        var request = URLRequest(url: url!)
        request.setValue(token, forHTTPHeaderField: "token")
        let session = URLSession.shared
        var res = ""
        let dataTask = session.dataTask(with: request,
                                        completionHandler: {(data, response, error) -> Void in
                                            if error != nil{
                                                print(error.debugDescription)
                                            }else{
                                                let str = String(data: data!, encoding: String.Encoding.utf8)
                                                res = str!
                                                
                                            }
        }) as URLSessionTask
        
        //使用resume方法启动任务
        dataTask.resume()
        while res == "" {
            
        }
        return res
        
    }
    
    
    
    
    
    func getOrderphoneNumber(token : String, param: String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken1(token: token, param: param) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderPurchaseInfoCom(token: token, param: param)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            // for id use this id\":
            if(productsToArray[b].contains("phoneNumber"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var temp2 = temp1.replacingOccurrences(of: "id", with: "")
                var temp3 = temp2.replacingOccurrences(of: "{", with: "")
                var temp4 = temp3.replacingOccurrences(of: "[", with: "")
                var mainPhoneNumber = temp4.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    func getOrdernickname(token : String, param: String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken1(token: token, param: param) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderPurchaseInfoCom(token: token, param: param)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            // for id use this id\":
            if(productsToArray[b].contains("nickname"))
            {
                let idx1 = productsToArray[b].characters.index(of:":")
                let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                var temp = productsToArray[b].substring(from:idx1!)
                var temp1 = temp.replacingOccurrences(of: ":", with: "")
                var temp2 = temp1.replacingOccurrences(of: "id", with: "")
                var temp3 = temp2.replacingOccurrences(of: "{", with: "")
                var temp4 = temp3.replacingOccurrences(of: "[", with: "")
                var mainPhoneNumber = temp4.replacingOccurrences(of: "\"", with: "")
                
                FinalProducts[k] = mainPhoneNumber
                
                
                k = k + 1
            }
            b = b + 1
        }
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    
    func getOrderToken1(token : String, param : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/userorder/seller?userOrderId=\(param)"
        let url = URL(string:urlString)
        //创建请求对象
        var request = URLRequest(url: url!)
        request.setValue(token, forHTTPHeaderField: "token")
        let session = URLSession.shared
        var res = ""
        let dataTask = session.dataTask(with: request,
                                        completionHandler: {(data, response, error) -> Void in
                                            if error != nil{
                                                print(error.debugDescription)
                                            }else{
                                                let str = String(data: data!, encoding: String.Encoding.utf8)
                                                res = str!
                                                
                                            }
        }) as URLSessionTask
        
        //使用resume方法启动任务
        dataTask.resume()
        while res == "" {
            
        }
        return res
        
    }
    
    
    
    func getOrderPurchaseInfoCom(token : String , param: String) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/userorder/seller?userOrderId=\(param)"
        let url = URL(string:urlString)
        //创建请求对象
        var request = URLRequest(url: url!)
        request.setValue(token, forHTTPHeaderField: "token")
        let session = URLSession.shared
        var res = ""
        let dataTask = session.dataTask(with: request,
                                        completionHandler: {(data, response, error) -> Void in
                                            if error != nil{
                                                print(error.debugDescription)
                                            }else{
                                                let str = String(data: data!, encoding: String.Encoding.utf8)
                                                res = str!
                                                
                                            }
        }) as URLSessionTask
        
        //使用resume方法启动任务
        dataTask.resume()
        while res == "" {
            
        }
        return res
        
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////// Order Oserration (finished)
    
    
    func changeDispatched(token : String, param : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/userorder/delivery?userOrderId=\(param)"
        let url = URL(string:urlString)
        //创建请求对象
        var request = URLRequest(url: url!)
        request.setValue(token, forHTTPHeaderField: "token")
        let session = URLSession.shared
        var res = ""
        let dataTask = session.dataTask(with: request,
                                        completionHandler: {(data, response, error) -> Void in
                                            if error != nil{
                                                print(error.debugDescription)
                                            }else{
                                                let str = String(data: data!, encoding: String.Encoding.utf8)
                                                res = str!
                                                
                                            }
        }) as URLSessionTask
        
        //使用resume方法启动任务
        dataTask.resume()
        while res == "" {
            
        }
        return res
        
    }
    
    
}


