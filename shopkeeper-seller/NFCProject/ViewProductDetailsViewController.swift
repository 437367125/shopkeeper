
import Foundation
import UIKit
class ViewProductDetailsViewController : UIViewController, UITableViewDataSource, UITableViewDelegate
{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return getInformation().count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = getInformation()[indexPath.row]
        return cell
    }
    
    @IBOutlet weak var label: UILabel!
    
    // when this page loads it will search the product from database using the segue
    override func viewDidLoad() {
        // serach from the database information of this product and put it here
        
    }
    
    public func getInformation() -> [String]
    {
        var name = String()
        var storageLocation = String()
        var price = String()
        var quantity = String()
        var imageURL = String()
        var des = String()
        
        
        name = productName[myIndex]
        storageLocation = productLocation[myIndex]
        price = productPrice[myIndex]
        quantity = productInventory[myIndex]
        imageURL = productPicture[myIndex]
        des = productDescription[myIndex]
        
        
        return ["Product name:   \(name)", "Loaction :   \(storageLocation)", "Price:   \(price)"
            ,"Quantity:   \(quantity)","Image URL:   \(imageURL)","des:   \(des)"]
    }
    
    
}
