package com.andjela.diplomski.utils;

import com.andjela.diplomski.common.AuthorityPermissionEnum;
import com.andjela.diplomski.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class AuthHelper {

    public static boolean currentUserIsSuperAdmin() {
        return currentUserHasAuthority(AuthorityPermissionEnum.SUPERADMIN);
    }

    public static boolean currentUserHasAuthority(AuthorityPermissionEnum authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if(CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        return authorities.stream().map(a -> a.getAuthority()).filter(a -> a.equalsIgnoreCase(authority.name())).findAny().isPresent();
//return true;
    }
}
