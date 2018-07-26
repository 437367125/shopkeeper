//
//  ProfileViewController.swift
//  NFCProject
//
//  Created by mahan shiran on 7/15/18.
//  Copyright © 2018 swiftsinglepage. All rights reserved.
//

import Foundation
import UIKit

class ProfileViewController: UIViewController, UITableViewDataSource, UITableViewDelegate
{
    @IBOutlet weak var Label: UILabel!
    @IBOutlet weak var myTable: UITableView!
    var labelText = String()
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return getInformation().count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = getInformation()[indexPath.row]
        return cell
    }
    

    
    
    
    override func viewDidLoad() {
       
        
    }
    
    @IBAction func refreshAction(_ sender: UIButton) {
        getInformation()
        myTable.reloadData()
    }
    
    public func getInformation() -> [String]
    {
        var username = String()
        var email = String()
        var phone = String()
        var name = String()
        
        username = MainUsername
        email = MainEmail
        phone = MainPhoneNumber
        name = MainNickname
        
        return ["Username:   \(username)", "Phone No:   \(phone)", "Email:   \(email)"
        ,"Name:   \(name)"]
    }

}
