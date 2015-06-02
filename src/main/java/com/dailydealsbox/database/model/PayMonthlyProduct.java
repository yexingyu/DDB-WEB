/**
 * 
 */
package com.dailydealsbox.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dailydealsbox.database.model.base.EntityBaseModel;

/**
 * @author y_dai
 */
@Entity
@Table(name = "pay_monthly_products")
public class PayMonthlyProduct extends EntityBaseModel {
}
