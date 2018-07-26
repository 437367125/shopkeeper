
import Foundation
import UIKit

var myIndex = 0;
var productName = ["first","second","third","forth"]
var productId = ["id","id","id","id"]
var productDescription = ["id","id","id","id"]
var productLocation = ["id","id","id","id"]
var productPrice = ["id","id","id","id"]
var productPicture = ["id","id","id","id"]
var productInventory = ["id","id","id","id"]
var idIndex = 0
class ProductManagementViewController : UIViewController, UITableViewDataSource, UITableViewDelegate
{
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var topLabel: UILabel!
    var token = String()
    

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return productName.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = productName[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        myIndex = indexPath.row
        performSegue(withIdentifier: "moveToDetails", sender: self)
    }
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == UITableViewCellEditingStyle.delete
        {
            productName.remove(at: indexPath.row)
            tableView.reloadData()
            var res = deleteProduct(token: token, param: productId[indexPath.row])
            if res.contains("0000")
            {
                refresh()
                createAlert(title: "", message: "删除成功！")
            }
            else
            {
                createAlert(title: "", message: "删除失败！")
            }
        }
    }

   
    func refresh()
    {
        productName = getProducts()
        productId = getProductsId()
        productDescription = getProductsDesccription()
        productLocation = getProductsLocation()
        productPrice = getProductsPrice()
        productPicture = getProductsPicture()
        productInventory = getProductsInventory()
        tableView.reloadData()
    }

    
    @IBAction func refreshAction(_ sender: UIButton) {
        refresh()
        print("refresh button tabbed")
    }
    
    
    
    override func viewDidLoad() {
        token = MainToken
        refresh()
       
      
        
     
        
    }
    
    
      //////////////////////////////////////////get Name of all products
        public func getProducts() -> [String]
        {
            let tokenInfo = getToken(token: token) as String
            var tokenArray = tokenInfo.components(separatedBy: ",")
            var products = getCom(token: token)
            var productsToArray = products.components(separatedBy: ",")
            var FinalProducts = productsToArray
            
            var s = productsToArray.count
            var b = 0
            var k = 0
            while(b < s)
            {
                
                if(productsToArray[b].contains("commodityName"))
                {
                    let idx1 = productsToArray[b].characters.index(of:":")
                    let pos1 = productsToArray[b].characters.distance(from:productsToArray[b].startIndex, to: idx1!)
                    var temp = productsToArray[b].substring(from:idx1!)
                    var temp2 = temp.replacingOccurrences(of: ":", with: "")
                    var mainPhoneNumber = temp2.replacingOccurrences(of: "\"", with: "")
                    
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
    
    
    func getCom(token : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/commodity/list"
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
      //////////////////////////////////////////get ID of all products
    func getProductsId() -> [String]
    {
      
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("id"))
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
        
        
        
        FinalProducts[0] = "1"
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
        }
        
        return FinalProducts
        
    }
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////get description of all products
    func getProductsDesccription() -> [String]
    {
        
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("description"))
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
    
    
    
    
    
    
    //////////////////////////////////////////get location of all products
    func getProductsLocation() -> [String]
    {
        
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("location"))
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////get Inventory of all products
    func getProductsInventory() -> [String]
    {
        
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("inventory"))
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
    
    
    
    //////////////////////////////////////////get Price of all products
    func getProductsPrice() -> [String]
    {
        
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("price"))
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
    
    
    
    
    //////////////////////////////////////////get Picture of all products
    func getProductsPicture() -> [String]
    {
        
        
        
        let tokenInfo = getToken(token: token) as String
        var tokenArray = tokenInfo.components(separatedBy: ",")
        var products = getCom(token: token)
        var productsToArray = products.components(separatedBy: ",")
        var FinalProducts = productsToArray
        
        var s = productsToArray.count
        var b = 0
        var k = 0
        while(b < s)
        {
            
            if(productsToArray[b].contains("picture"))
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
    func deleteProduct(token : String, param : String ) -> String {
        //创建URL对象
        let urlString = "http://120.79.132.224:9090/shopkeeper/commodity/delete?commodityId=\(param)"
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
    func createAlert(title: String, message: String) // Creates an message box
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        self.present(alert, animated: true, completion: nil)
    }
    
    
    
}
