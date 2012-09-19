/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

public class Redirector implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void redirect(String viewId) {
		redirect(viewId, null);
	}

	public static void redirect(String viewId, Map<String, Object> params) {
		try {
			if (viewId != null && !viewId.isEmpty()) {
				Boolean includeViewParams = true;
				if (params.isEmpty()) {
					includeViewParams = false;
				}

				FacesContext facesContext = Beans.getReference(FacesContext.class);
				ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
				String url = viewHandler.getBookmarkableURL(facesContext, viewId, parse(params), includeViewParams);

				facesContext.getExternalContext().redirect(url);
			}

		} catch (NullPointerException cause) {
			throw new PageNotFoundException(viewId);

		} catch (IOException cause) {
			throw new FacesException(cause);
		}
	}

	private static Map<String, List<String>> parse(Map<String, Object> map) {
		Map<String, List<String>> result = null;

		if (map != null) {
			ArrayList<String> list;
			result = new HashMap<String, List<String>>();

			for (String key : map.keySet()) {
				list = new ArrayList<String>();
				list.add(map.get(key).toString());
				result.put(key, list);
			}
		}

		return result;
	}
}
