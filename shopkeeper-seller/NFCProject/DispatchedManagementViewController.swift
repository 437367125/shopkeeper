import Foundation
import UIKit

var myIndex3 = 0;
var purchasesNumber = ["订单1","订单2","订单3"]
var purchasesId = ["订单1","订单2","订单3"]
class DispatchedManagementViewController : UIViewController,UITableViewDataSource, UITableViewDelegate
{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return purchasesNumber.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell")
        if(purchasesNumber[indexPath.row]  != "")
        {
        cell.textLabel?.text = purchasesNumber[indexPath.row]
        }
        return cell
    }
    

    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 55
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        myIndex3 = indexPath.row
        performSegue(withIdentifier: "segue", sender: self)
    }
    
    public func updateformation() -> [String] // get data from database and update the array here
    {
        purchasesNumber = getOrderNumber(token: MainToken)
        return purchasesNumber
        
        
    }
    
    
    
    
    
    
    override func viewDidLoad() {
        purchases = updateformation()
        purchasesId = getOrderId(token: MainToken)
    }
    
    
    
    
    
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////// Order Oserration
    
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////get ID of all orders
    func getOrderId(token : String) -> [String]
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
        
        
        var c = 0
        var y = FinalProducts.count
        while(k < s)
        {
            FinalProducts[k] = ""
            k = k + 1
            
        }
        
        return FinalProducts
        
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////get number of all orders
    func getOrderNumber(token : String) -> [String]
    {
        
        
        var num = 0
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
            
            if(productsToArray[b].contains("orderNumber"))
            {
                num = num + 1
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
