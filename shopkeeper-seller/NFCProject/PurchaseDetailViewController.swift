//
//  PurchaseDetailViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/18/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit
class PurchaseDetailViewController : UIViewController,UITableViewDelegate,UITableViewDataSource
{
    @IBOutlet weak var TitleLAbel: UILabel!
    var info = [""]
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return info.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell1")
        cell.textLabel?.text = info[indexPath.row]
        return cell
    }
    
    override func viewDidLoad() {
        purchasestotalPrice = getOrderPrice(token: MainToken)
        purchasesaddressId = getOrderaddressId(token: MainToken)
        purchasesstatus = getOrderstatus(token: MainToken)
        purchasescreateTime = getOrdercreateTime(token: MainToken)
        purchasespayTime = getOrderpayTime(token: MainToken)
        purchasesdeliveryTime = getOrderdeliveryTime(token: MainToken)
        purchasescompleteTime = getOrdercompleteTime(token: MainToken)
        purchasescancelTime = getOrdercancelTime(token: MainToken)
        
       info = getInformation()
        TitleLAbel.text = "\(purchases[myIndex2]) Details"
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
        
        
        
        
        
        
        number = purchasesNumber[myIndex2]
        totalPrice = purchasestotalPrice[myIndex2]
        addressId = purchasesaddressId[myIndex2]
        status = purchasesstatus[myIndex2]
        createTime = purchasescreateTime[myIndex2]
        payTime = purchasespayTime[myIndex2]
        deliveryTime = purchasesdeliveryTime[myIndex2]
        completeTime = purchasescompleteTime[myIndex2]
        cancelTime = purchasescancelTime[myIndex2]
        
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
        return ["订单号： \(number)" , "Total Price：  \(totalPrice)","Address ID：  \(addressId)","Status：  \(status)","Create Time \(createTime)","Pay Time \(payTime)","Delivery Time \(deliveryTime)","Complete Time \(completeTime)","Cancel Time \(cancelTime)"]
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
    
    //////////////////////////////////////////////////////////////////////////////////////////////////// Order Oserration (finished)
    
    
    
}
