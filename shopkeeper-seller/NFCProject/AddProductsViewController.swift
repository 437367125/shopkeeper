//
//  AddProductsViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/16/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit
class AddProductsViewController : UIViewController, UITextFieldDelegate
{
    
    @IBOutlet weak var productName: UITextField!
    @IBOutlet weak var storingPlace: UITextField!
    @IBOutlet weak var price: UITextField!
    @IBOutlet weak var quantity: UITextField!
    @IBOutlet weak var imageURL: UITextField!
    @IBOutlet weak var descrip: UITextField!
    @IBOutlet weak var type: UITextField!
    
    
    
    @IBAction func AddProductAction(_ sender: UIButton)
    {
        
        var productNameVar = productName.text as! String
        var storingPlaceVar = self.storingPlace.text as! String
        var priceVar = self.price.text as! String
        var quantityVar = self.quantity.text as! String
        var imageURLVar = self.imageURL.text as! String
        var descripVar = self.descrip.text as! String
        var typeVar = self.type.text as! String
        
        
        
        if productName.text == "" || storingPlace.text == "" || price.text == "" || quantity.text == "" || imageURL.text == "" || descrip.text == "" || type.text == ""
        {
            createAlert(title: "Could not add Product!", message: "Please fill in all Fields given.")
        }
        else // if everything OJBK
        {
            addProduct("http://120.79.132.224:9090/shopkeeper/commodity", paramString: "commodityName=\(productNameVar)&description=\(descripVar)&location=\(storingPlaceVar)&inventory=\(quantityVar)&price=\(priceVar)&picture=\(imageURLVar)&type=\(typeVar)",token: MainToken)
            createAlert(title: "", message: "Product \"\(productNameVar)\" Added!")
            
        }
        
        print("add button tabbed")
        
        
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
        self.productName.delegate = self
        self.storingPlace.delegate = self
        self.price.delegate = self
        self.type.delegate = self
        self.quantity.delegate = self
        self.imageURL.delegate = self
        self.descrip.delegate = self
        
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
        
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return (true)
    }
    
    func addProduct(_ url:String, paramString: String, token : String) -> String
    {
        let url:NSURL = NSURL(string: url)!
        let session = URLSession.shared
        
        let request = NSMutableURLRequest(url: url as URL)
        request.httpMethod = "POST"
        request.setValue(token, forHTTPHeaderField: "token")
        
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
