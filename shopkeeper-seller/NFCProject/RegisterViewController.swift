//
//  RegisterViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/15/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit

class RegisterViewController : UIViewController, UITextFieldDelegate
{
    @IBOutlet weak var testLabel: UILabel!
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var phoneTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var repasswordTextField: UITextField!
    
    
    var username1 = String()
    var phone1 = String()
    var email1 = String()
    var name1 = String()
    var password1 = String()
    var repassword1 = String()
    
    
    
    
    
    
    @IBAction func registerAction(_ sender: UIButton) { // register button pressed
        
        username1 = usernameTextField.text! as String
        phone1 = phoneTextField.text! as String
        email1 = emailTextField.text! as String
        name1 = nameTextField.text! as String
        password1 = passwordTextField.text! as String
        repassword1 = repasswordTextField.text! as String
        
        
        
      
        
        // check if any TextField is empty
        if usernameTextField.text == "" || phoneTextField.text == "" ||
        emailTextField.text == "" || nameTextField.text == "" ||
        passwordTextField.text == "" || repasswordTextField.text == ""
        {
                createAlert(title: "Register Failed", message: "Please fill in all of the fields!")
        }
        else
        {
         
        
            //check email
             if !email1.contains("@")
            {
                createAlert(title: "", message: "Please enter a valid email!")
                emailTextField.text = ""
            }
            else if  name1.count < 3
            {
                createAlert(title: "", message: "Name should have more than 2 characters!")
                nameTextField.text = ""
            }
            else if  phone1.count <= 10
            {
                createAlert(title: "", message: "Please enter a valid phone number!")
                phoneTextField.text = ""
            }
            //check passwords
            else if password1 != repassword1
            {
                createAlert(title: "", message: "Passwords does not match!")
                passwordTextField.text = ""
                repasswordTextField.text = ""
            }
            
               var res = data_request("http://120.79.132.224:9090/shopkeeper/user",paramString: "username=\(username1)&phoneNumber=\(phone1)&email=\(email1)&nickname=\(name1)&password=\(password1)&type=0")
            
             if res.contains("0000") && res.contains("操作成功")
             {
                createAlert(title: "注册成功！", message: "请登录。")
             }
             else if res.contains("0006") && res.contains("用户名已存在")
             {
                createAlert(title: "用户名已存在", message: "请输入其他用户名。")
             }
            else if res.contains("0000") || res.contains("0006")
             {
                 createAlert(title: "", message: "抱歉，注册失败！")
             }
        }
        
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        print("Register button clicked!")
        
    }
    
    
    
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return (true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        
    }
    
    @IBAction func GoToLogin(_ sender: UIButton) {
        performSegue(withIdentifier: "segue2", sender: nil)
    }
    
    @IBAction func GoToLogin2(_ sender: UIButton) {
           performSegue(withIdentifier: "segue2", sender: nil)
    }
    
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    
    
    
}

