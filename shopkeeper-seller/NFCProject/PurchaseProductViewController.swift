//
//  PurchaseProductViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/24/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit
var myIndex6 = 0

class PurchaseProductViewController: UIViewController,UITableViewDataSource, UITableViewDelegate
{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return getInformation1().count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell3")
        cell.textLabel?.text = getInformation1()[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 70
    }
    
    
    
    override func viewDidLoad() {

        
        
    }
    
    public func getInformation1() -> [String]
    {

        return getOrderId(token: MainToken, param: purchasesId[myIndex3])
        
    }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    func getOrderId(token : String, param: String) -> [String]
    {
        
        
        
        let tokenInfo = getOrderToken1(token: token, param: param) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getOrderPurchaseInfoCom(token: token, param: param)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = ["", "", "","",""]
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            // for id use this id\":
            if(productsToArray[b].contains("commodityName"))
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
    
    
}
