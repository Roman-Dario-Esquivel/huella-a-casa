package ar.com.huella.huella.service;

import ar.com.huella.huella.entity.Resolved;


public interface ICaseMigrationService {
    Resolved migrateLostToResolved(Long lostId);
    Resolved migrateFoundToResolved(Long foundId);
}
