

import Foundation
import UIKit
class EditProfileViewController : UIViewController , UITextFieldDelegate
{
    
    @IBOutlet weak var testLabel: UILabel!
    
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var phoneTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var nameTextField: UITextField!

    
    var username = String()
    var phone = String()
    var email = String()
    var name = String()

    
    
    @IBAction func DoneAction(_ sender: UIButton) {
       
        username = usernameTextField.text as! String
        phone = phoneTextField.text as! String
        email = emailTextField.text as! String
        name = nameTextField.text as! String
        
        if usernameTextField.text == "" || phoneTextField.text == ""||emailTextField.text == "" || nameTextField.text == ""
        {
            createAlert(title: "操作失败", message: "Please fill in all of the fields!")
        }
        else
        {
            if !email.contains("@")
            {
                createAlert(title: "", message: "Please enter a valid email!")
                emailTextField.text = ""
            }
            else if  name.count < 3
            {
                createAlert(title: "", message: "Name should have more than 2 characters!")
                nameTextField.text = ""
            }
            else if  phone.count <= 10
            {
                createAlert(title: "", message: "Please enter a valid phone number!")
                phoneTextField.text = ""
            }
            else // if everything OJBK
            
            {
              updateProfile("http://120.79.132.224:9090/shopkeeper/user/info",paramString: "nickname=\(name) &phoneNumber=\(phone)&\(email)",token: MainToken)
                createAlert(title: "", message: "编辑资料成功！")
                username = MainUsername
                email = MainEmail
                phone = MainPhoneNumber
                name = MainNickname
            }
        }
        
        print("Done button tabbed")
    }
    

    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
         self.view.endEditing(true)
    }
    
    
    
    
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        usernameTextField.text = MainUsername as String
        phoneTextField.text = MainPhoneNumber as String
        emailTextField.text = MainEmail as String
        nameTextField.text = MainNickname as String
        
        self.usernameTextField.delegate = self
        self.phoneTextField.delegate = self
        self.emailTextField.delegate = self
        self.nameTextField.delegate = self

        
    }

    func updateProfile(_ url:String, paramString: String, token:String) -> String
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
    
}












