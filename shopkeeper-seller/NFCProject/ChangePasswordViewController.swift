//
//  ChangePasswordViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/24/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation

import UIKit
class ChangePasswordViewController : UIViewController,UITextFieldDelegate
{
    
    @IBOutlet weak var lastPassword: UITextField!
    @IBOutlet weak var newPassword: UITextField!
    @IBOutlet weak var reNewPassword: UITextField!
    
    
    
    @IBAction func changePasswordAction(_ sender: UIButton) {
        var lpass = lastPassword.text as! String
        var npass = newPassword.text as! String
        var renpass = reNewPassword.text as! String
        
        if lastPassword.text == "" || newPassword.text == "" || reNewPassword.text == ""
        {
            createAlert(title: "改密码失败！", message: "密码不能空！")
            
        }
        else
        {
            if lpass == MainPassword
            {
                
                var result = updateSomething("http://120.79.132.224:9090/shopkeeper/user/password", paramString: "oldPassword=\(lpass)&newPassword=\(npass)", token: MainToken) as String
                if result.contains("0000")
                {
                                  createAlert(title: "", message: "改密码成功！")
                }
                else
                {
                                  createAlert(title: "", message: "改密码失败！")
                }
                
                
            }
            else
            {
                createAlert(title: "改密码失败！", message: "请输入正确的密码")
            }
        }
    }
    
    
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    func updateSomething(_ url:String, paramString: String, token:String) -> String
    {
        
        let url:NSURL = NSURL(string: url)!
        let session = URLSession.shared
        
        let request = NSMutableURLRequest(url: url as URL)
        request.setValue(token, forHTTPHeaderField: "token")
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
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return (true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        
    }
    
    
}
