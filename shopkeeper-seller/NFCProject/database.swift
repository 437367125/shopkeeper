//
//  database.swift
//  NFCProject
//
//  Created by mahan shiran on 7/19/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation

func data_request(_ url:String, paramString: String) -> String
{
    
    let url:NSURL = NSURL(string: url)!
    let session = URLSession.shared
    
    let request = NSMutableURLRequest(url: url as URL)
    request.httpMethod = "POST"
    
    request.httpBody = paramString.data(using: String.Encoding.utf8)
    var res = ""
    let task = session.dataTask(with: request as URLRequest) {
        (
        data, response, error) in
        
        guard let _:NSData = data as NSData?, let _:URLResponse = response, error == nil else {
            print("error")
            return
        }
        
        if let dataString = NSString(data: data!, encoding: String.Encoding.utf8.rawValue)
        {
            res = dataString as String
        }
    }
    
    task.resume()
    while res == "" {
        
    }
    return res
    
}

func getToken(token : String ) -> String {
    //创建URL对象
    let urlString = "http://120.79.132.224:9090/shopkeeper/user"
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



