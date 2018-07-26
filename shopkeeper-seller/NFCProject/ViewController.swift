//
//  ViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/14/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import UIKit

public var MainUsername = "null" as String
public var MainPhoneNumber = "null" as String
public var MainEmail = "null" as String
public var MainNickname = "null" as String
public var MainToken = "null" as String
public var MainPassword = "null" as String


class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var usernamejtf: UITextField!
    @IBOutlet weak var passwordjtf: UITextField!
    var loginAllowed = false;
    
    
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        self.usernamejtf.delegate = self
        self.passwordjtf.delegate = self
     
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    @IBAction func loginAction(_ sender: UIButton) {
       
        var username = usernamejtf.text as! String
        var password = passwordjtf.text as! String
        
        if username == "" && password == ""
        {
           createAlert(title: "login failed!", message: "Username and Pasword can not be empty!" )
        }
        else if username == ""
        {
             createAlert(title: "login failed!", message: "Username can not be empty!" )
        }
        else if password == ""
        {
            createAlert(title: "login failed!", message: "Password can not be empty!" )
        }
        else
        {
            var result1 = data_request("http://120.79.132.224:9090/shopkeeper/user/token", paramString: "account=\(username)&password=\(password)&method=0")
            
            if result1.contains("0000")
            {
                MainPassword = password
                performSegue(withIdentifier: "segue1", sender: nil)
                getTokenInfo(result: result1)
                
            }
               else
                {
                      createAlert(title: "login failed!", message: "Username or Password not correct!" )
                }
            
        }
     
        print("login buttom pressed")
    }
    
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return (true)
    }
    
    
    
    
    
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    @IBAction func registerAction(_ sender: UIButton) {
        performSegue(withIdentifier: "segueForRegister", sender: nil)
        print("register")
    }
    
    func getTokenInfo(result : String)
    {
        var fullNameArr = result.components(separatedBy: "\"")
        var j = fullNameArr.count
        var i = 0
        
        var token = fullNameArr[j-10]
        MainToken = token
        
        let tokenInfo = getToken(token: token) as String
        
        
        var tokenArray = tokenInfo.components(separatedBy: ",")
        
        
        var username1 = tokenArray[1] as String
        let phoneNumber1 = tokenArray[2] as String
        let email1 = tokenArray[3] as String
        let nickname1 = tokenArray[4] as String
        
        
        
        
        var username = username1.replacingOccurrences(of: "\"", with: "")
        let idx = username.characters.index(of:":")
        let pos = username.characters.distance(from:username.startIndex, to: idx!)
        var username2 = username.substring(from:idx!)
        var mainUsername = username2.replacingOccurrences(of: ":", with: "")
        //phone number
        var phoneNumber = phoneNumber1.replacingOccurrences(of: "\"", with: "")
        let idx1 = phoneNumber.characters.index(of:":")
        let pos1 = phoneNumber.characters.distance(from:phoneNumber.startIndex, to: idx1!)
        var phoneNumber2 = phoneNumber.substring(from:idx1!)
        var mainPhoneNumber = phoneNumber2.replacingOccurrences(of: ":", with: "")
        //email
        var email = email1.replacingOccurrences(of: "\"", with: "")
        let idx2 = email.characters.index(of:":")
        let pos2 = email.characters.distance(from:email.startIndex, to: idx2!)
        var email2 = email.substring(from:idx2!)
        var mainEmail = email2.replacingOccurrences(of: ":", with: "")
        //nickname
        var nickname = nickname1.replacingOccurrences(of: "\"", with: "")
        let idx3 = nickname.characters.index(of:":")
        let pos3 = nickname.characters.distance(from:nickname.startIndex, to: idx3!)
        var nickname2 = nickname.substring(from:idx3!)
        var mainNickname = nickname2.replacingOccurrences(of: ":", with: "")
        
        
        
        
        MainUsername = mainUsername
        MainPhoneNumber = mainPhoneNumber
        MainEmail = mainEmail
        MainNickname = mainNickname
        
    }   
    


}

